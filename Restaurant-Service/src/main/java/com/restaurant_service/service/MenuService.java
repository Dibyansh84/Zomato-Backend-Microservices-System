package com.restaurant_service.service;

import com.restaurant_service.DTO.MenuRequestDTO;
import com.restaurant_service.DTO.MenuResponseDTO;
import jakarta.validation.Valid;

import java.util.List;

public interface MenuService {
    MenuResponseDTO createMenu(@Valid MenuRequestDTO requestDTO);

    List<MenuResponseDTO> getMenusByRestaurantId(Long restaurantId);
}
