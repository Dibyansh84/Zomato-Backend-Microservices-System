package com.restaurant_service.controller;

import com.restaurant_service.DTO.FoodItemRequestDTO;
import com.restaurant_service.DTO.FoodItemResponseDTO;
import com.restaurant_service.service.FoodItemService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/food")
@Slf4j
@Validated
public class FoodItemController
{
    /* @Validated specifies validation groups to be applied at the class or method level. */

    @Autowired
    private FoodItemService foodItemService;

    //if you want to bypass the error in console temporarily and just see the response, try adding produces = MediaType.APPLICATION_JSON_VALU
    @PostMapping(value= "/createFoodItem", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FoodItemResponseDTO> createFoodItem(@Valid @RequestBody FoodItemRequestDTO requestDTO)
    {
        return ResponseEntity.ok(foodItemService.createFoodItem(requestDTO));
    }


    @PostMapping("/getFoodItems")
    public ResponseEntity<List<FoodItemResponseDTO>> getFoodItems(@RequestParam Long menuId)
    {
        List<FoodItemResponseDTO> foodItemResponseDTOS = foodItemService.getFoodItems(menuId);
        return ResponseEntity.ok(foodItemResponseDTOS);
    }
}
