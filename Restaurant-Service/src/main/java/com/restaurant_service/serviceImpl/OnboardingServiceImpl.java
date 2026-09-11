package com.restaurant_service.serviceImpl;

import com.restaurant_service.DTO.OnboardingStatusRequestDTO;
import com.restaurant_service.DTO.OnboardingStatusResponseDTO;
import com.restaurant_service.entity.OnboardingStatus;
import com.restaurant_service.exception.BadRequestException;
import com.restaurant_service.exception.ResourceNotFoundException;
import com.restaurant_service.repository.OnboardingStatusCustomRepository;
import com.restaurant_service.repository.OnboardingStatusJpaRepository;
import com.restaurant_service.repository.RestaurantJpaRepository;
import com.restaurant_service.service.OnboardingStatusService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class OnboardingServiceImpl implements OnboardingStatusService
{
    private final OnboardingStatusJpaRepository onboardingStatusJpaRepository;
    private final RestaurantJpaRepository restaurantJpaRepository;
    private final OnboardingStatusCustomRepository onboardingStatusCustomRepository;

    public OnboardingServiceImpl(RestaurantJpaRepository restaurantJpaRepository,
                                 OnboardingStatusJpaRepository onboardingStatusJpaRepository,
                                 OnboardingStatusCustomRepository onboardingStatusCustomRepository)
    {
        this.restaurantJpaRepository = restaurantJpaRepository;
        this.onboardingStatusJpaRepository = onboardingStatusJpaRepository;
        this.onboardingStatusCustomRepository = onboardingStatusCustomRepository;
    }


    /**
     * Update restaurant onboarding status.
     *
     * Possible values:
     * PENDING
     * UNDER_REVIEW
     * APPROVED
     * REJECTED
     */
    @Override
    @Transactional
    @CacheEvict(value = "onboardingCache", allEntries = true)
    public OnboardingStatusResponseDTO updateStatus(OnboardingStatusRequestDTO requestDTO)
    {
        log.info("Updating onboarding status: {}", requestDTO.getStatus());
        /*
         * Step 1:
         * Validate whether restaurant exists or not.
         */
        if(!restaurantJpaRepository.existsById(requestDTO.getRestaurantId()))
        {
            throw new ResourceNotFoundException("Restaurant not found");
        }

        /* Step 2: Save onboarding history
        *
        * Every status change is stored.
        * This provides complete audit history.*/

        OnboardingStatus onboardingStatus = new OnboardingStatus();
        BeanUtils.copyProperties(requestDTO, onboardingStatus);

        onboardingStatus.setApprovedOn(LocalDateTime.now());

        OnboardingStatus savedStatus = onboardingStatusJpaRepository.save(onboardingStatus);

        /* Step 3: Update onboarding completed
        * flag in restaurants table or  Mark the restaurant as ready or not based on its approval status*/
        switch(requestDTO.getStatus())
        {
            // Restaurant approved - mark onboarding as done
            case APPROVED ->
                restaurantJpaRepository.updateOnboardingStatus(requestDTO.getRestaurantId(), true);

            // Restaurant not approved yet - mark onboarding as incomplete
            case PENDING, UNDER_REVIEW, REJECTED ->
                restaurantJpaRepository.updateOnboardingStatus(requestDTO.getRestaurantId(), false);

            // Unknown status - something went wrong
            default ->
                throw new BadRequestException("Invalid Onboarding Status");
        }

        /* Step 4: Convert Entity -> Restaurant DTO*/
        OnboardingStatusResponseDTO responseDTO = new OnboardingStatusResponseDTO();

        BeanUtils.copyProperties(savedStatus, responseDTO);

        /* Step 5: Return response*/
        return responseDTO;
    }

    /* Fetch onboarding history for a restaurant*/
    @Override
    @Cacheable(value = "onboardingCache", key = "#restaurantId")
    public List<OnboardingStatusResponseDTO> getOnboardingStatusHistory(Long restaurantId)
    {
        log.info("Fetching onboarding history for restaurant: {}", restaurantId);

        List<OnboardingStatusResponseDTO> statusData = onboardingStatusCustomRepository.getOnboardingStatus(restaurantId);
        return statusData;
    }
}
