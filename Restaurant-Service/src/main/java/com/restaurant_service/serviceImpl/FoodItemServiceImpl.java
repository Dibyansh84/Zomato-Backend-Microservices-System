package com.restaurant_service.serviceImpl;

import com.restaurant_service.DTO.FoodItemRequestDTO;
import com.restaurant_service.DTO.FoodItemResponseDTO;
import com.restaurant_service.entity.FoodItem;
import com.restaurant_service.exception.ResourceNotFoundException;
import com.restaurant_service.repository.FoodItemCustomRepository;
import com.restaurant_service.repository.FoodItemJpaRepository;
import com.restaurant_service.repository.MenuJpaRepository;
import com.restaurant_service.service.FoodItemService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class FoodItemServiceImpl implements FoodItemService
{
    // Repository for food item database operations
    private final FoodItemJpaRepository foodItemJpaRepository;
    // Repository for menu database operations
    private final MenuJpaRepository menuJpaRepository;

    private final FoodItemCustomRepository foodItemCustomRepository;

    /*Constructor for dependency injection of required repositories
    * @param menuJpaRepository Repository for menu DB operations
    * @param foodItemJpaRepository for food item DB operations*/
    public FoodItemServiceImpl(MenuJpaRepository menuJpaRepository,
                               FoodItemJpaRepository foodItemJpaRepository, FoodItemCustomRepository foodItemCustomRepository) {
        this.menuJpaRepository = menuJpaRepository;
        this.foodItemJpaRepository = foodItemJpaRepository;
        this.foodItemCustomRepository = foodItemCustomRepository;
    }

    /**
     * Creates a new food item and associates it with an existing menu.
     *
     * @param requestDTO Data transfer object containing food item details
     * @return FoodItemResponseDTO containing the created food item information
     * @throws ResourceNotFoundException if the specified menu does not exist
     */

    @Override
    @Transactional
    @CacheEvict(value="foodCache", allEntries = true)
    public FoodItemResponseDTO createFoodItem(@Valid FoodItemRequestDTO requestDTO)
    {
        // Log the creation request with item name for auditing purposes
        log.info("Creating food item: {}", requestDTO.getItemName());

        // Validate that the menu exists in the database before creating the food item
        if(!menuJpaRepository.existsById(requestDTO.getMenuId()))
        {
            throw new ResourceNotFoundException("Menu not found");
        }

        // Create a new FoodItem entity instance
        FoodItem item = new FoodItem();

        // Copy properties from request DTO to entity (maps matching field names)
        BeanUtils.copyProperties(requestDTO, item);
        item.setName(requestDTO.getItemName()); // ✅ Map itemName → name

        try {

            // Store the food item to the database and retrieve the saved instance with generated ID
            FoodItem savedItem = foodItemJpaRepository.save(item);

            // Create response DTO to send back to client
            FoodItemResponseDTO response = new FoodItemResponseDTO();

            // Copy properties from saved entity to response DTO
//            BeanUtils.copyProperties(savedItem, response);
            response.setId(savedItem.getId());
            response.setMenuId(savedItem.getMenuId());
            // Map name → itemName in response if needed
            response.setItemName(savedItem.getName());
            response.setDescription(savedItem.getDescription());
            response.setCategory(savedItem.getCategory());
            response.setIsVeg(savedItem.getIsVeg());
            response.setPrice(savedItem.getPrice());
            response.setAvailable(savedItem.getAvailable());
            response.setRating(savedItem.getRating());


            // Return the response containing the created food item details
            return response;
        }
        catch (Exception e)
        {
            log.error("Error creating response DTO", e); //Log the actual error
            throw e;
        }
    }

    @Override
    @Cacheable(value = "foodCache", key = "#menuId")
    public List<FoodItemResponseDTO> getFoodItems(Long menuId)
    {
        return foodItemCustomRepository.getFoodItems(menuId);
    }
}
