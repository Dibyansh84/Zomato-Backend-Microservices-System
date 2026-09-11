package com.restaurant_service.controller;

import com.restaurant_service.DTO.OnboardingStatusRequestDTO;
import com.restaurant_service.DTO.OnboardingStatusResponseDTO;
import com.restaurant_service.service.OnboardingStatusService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/onboarding")
@Slf4j
@Validated
public class OnboardingStatusController
{
    @Autowired
    private OnboardingStatusService onboardingStatusService;

    /* Update Onboarding status*/
    @PostMapping("/updateStatus")
    public ResponseEntity<OnboardingStatusResponseDTO> updateStatus(@Valid @RequestBody OnboardingStatusRequestDTO requestDTO)
    {
        log.info("Updating onboarding status");

        OnboardingStatusResponseDTO statusResponseDTO = onboardingStatusService.updateStatus(requestDTO);
        return ResponseEntity.ok(statusResponseDTO);
    }

    @PostMapping("/getHistory")
    public ResponseEntity<List<OnboardingStatusResponseDTO>> getHistory(@RequestParam Long restaurantId)
    {
        log.info(" Fetching onboarding history ");

        List<OnboardingStatusResponseDTO> getOnboardingStatusHistory =onboardingStatusService.getOnboardingStatusHistory(restaurantId);
        return ResponseEntity.ok(getOnboardingStatusHistory);
    }


}
