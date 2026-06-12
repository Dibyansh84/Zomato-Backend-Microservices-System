package com.restaurant_service.service;

import com.restaurant_service.DTO.FoodItemRequestDTO;
import com.restaurant_service.DTO.FoodItemResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface FoodItemService {
    FoodItemResponseDTO createFoodItem(@Valid FoodItemRequestDTO requestDTO);

    List<FoodItemResponseDTO> getFoodItems(Long menuId);
}
