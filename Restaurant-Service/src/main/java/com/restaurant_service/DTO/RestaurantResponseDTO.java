package com.restaurant_service.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestaurantResponseDTO implements Serializable
{
   /*Spring Redis default serializer uses Java Serialization.
So cached objects must implement: Serializable interface*/
    private Long id;
    private String restaurantName;
    private String ownerName;
    private String email;
    private String phone;
    private String city;
    private String address;
    private String cuisineType;
    private Double rating;
    private Boolean active;
}
