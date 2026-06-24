package com.restaurant_service.service;

import com.restaurant_service.DTO.RestaurantDocumentRequestDTO;
import com.restaurant_service.DTO.RestaurantDocumentResponseDTO;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;

import java.util.List;

public interface RestaurantDocumentService
{
    RestaurantDocumentResponseDTO uploadDocument(@Valid RestaurantDocumentRequestDTO request);

    List<RestaurantDocumentResponseDTO> getDocuments(Long restaurantId);

    void generateRestaurantDocumentListPdf(HttpServletResponse response, Long restaurantId) throws Exception;
}
