package com.restaurant_service.DTO;

import lombok.Data;

@Data
public class RestaurantSearchRequestDTO
{
    private String city;
    private String cuisineType;
    private Double minRating;
}
