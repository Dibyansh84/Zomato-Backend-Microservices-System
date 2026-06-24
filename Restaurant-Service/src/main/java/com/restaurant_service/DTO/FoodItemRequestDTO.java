package com.restaurant_service.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FoodItemRequestDTO
{
    @NotNull(message = "Menu id is required")
    private Long menuId;
    @NotBlank(message = "Item cannot be blank")
    private String itemName;
    private String description;
    private String category;
    private Boolean isVeg;
    @NotNull(message = "Price is required")
    private Double price;
}
