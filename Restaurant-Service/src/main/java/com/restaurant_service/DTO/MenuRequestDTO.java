package com.restaurant_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class MenuRequestDTO
{
    @NotNull(message = "Restaurant id is required")
    private Long restaurantId;

    @NotBlank(message = "Menu name is required")
    @Size(max = 100, message = "Menu name cannot exceed 100 characters")
    private String menuName;
}
