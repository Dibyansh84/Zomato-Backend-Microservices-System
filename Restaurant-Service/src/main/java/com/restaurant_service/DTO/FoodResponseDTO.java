package com.restaurant_service.DTO;

import lombok.Data;

@Data
public class FoodResponseDTO
{
    private Long id;
    private Long menuId;
    private String itemName;
    private String description;
    private String category;
    private Boolean veg;
    private Double price;
    private Boolean available;
    private Double rating;
}
