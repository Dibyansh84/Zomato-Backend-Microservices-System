package com.restaurant_service.DTO;

import lombok.Data;

import java.io.Serializable;

@Data
public class RestaurantDocumentResponseDTO implements Serializable
{
    //Since you are probably using Redis cache, cached objects must implement Serializable interface.
    private Long id;
    private String restaurantName;
    private Long restaurantId;
    private String documentType;
    private String documentUrl;
    private Boolean verified;
}
