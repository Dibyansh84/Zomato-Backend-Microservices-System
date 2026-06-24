package com.restaurant_service.service;

import com.restaurant_service.DTO.RestaurantRequestDTO;
import com.restaurant_service.DTO.RestaurantResponseDTO;
import com.restaurant_service.DTO.RestaurantSearchRequestDTO;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface RestaurantService
{
    RestaurantResponseDTO createRestaurant(RestaurantRequestDTO request);

    RestaurantResponseDTO getRestaurantById(Long restaurantId);

    Page<RestaurantResponseDTO> getRestaurantList(int page, int size);

    List<RestaurantResponseDTO> searchRestaurants(RestaurantSearchRequestDTO request);

    void generateRestaurantListPdf(HttpServletResponse response) throws Exception;
}
