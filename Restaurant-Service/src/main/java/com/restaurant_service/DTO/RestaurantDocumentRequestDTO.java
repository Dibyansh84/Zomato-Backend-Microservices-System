package com.restaurant_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RestaurantDocumentRequestDTO
{
    @NotNull(message = "restaurant id is required")
    private Long restaurantId;
    @NotBlank(message = "Item cannot be blank")
    private String documentType;
    @NotBlank
    private String documentUrl;
}
