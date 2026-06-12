package com.restaurant_service.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class RestaurantRequestDTO
{
    @NotBlank
    private String restaurantName;

    @NotBlank
    private String ownerName;

    @Email
    private String email;

    @NotBlank
    private String phone;

    @NotBlank
    private String city;

    private String address;

    private String cuisineType;
}
