package com.restaurant_service.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class FoodItemResponseDTO implements Serializable
{
    //Since you are probably using Redis cache, cached objects must implement Serializable interface.
    private Long id;
    private Long menuId;
    private String itemName;
    private String description;
    private String category;
    private Boolean isVeg;
    private Double price;
    private Boolean available;
    private Double rating;
}
