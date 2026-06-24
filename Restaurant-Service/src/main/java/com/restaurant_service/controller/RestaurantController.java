package com.restaurant_service.controller;

import com.restaurant_service.DTO.RestaurantRequestDTO;
import com.restaurant_service.DTO.RestaurantResponseDTO;
import com.restaurant_service.DTO.RestaurantSearchRequestDTO;
import com.restaurant_service.service.RestaurantService;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/restaurant")
@Slf4j
@Validated
public class RestaurantController
{
    @Autowired
    private RestaurantService restaurantService;

    @PostMapping("/create")
    public ResponseEntity<RestaurantResponseDTO> createRestaurant(@Valid @RequestBody RestaurantRequestDTO request)
    {
        log.info("Creating restaurant");

        return ResponseEntity.ok(restaurantService.createRestaurant(request));
    }

    @PostMapping("/getRestaurantById")
    public ResponseEntity<RestaurantResponseDTO> getRestaurantById(@RequestParam Long restaurantId)
    {
        log.info("Fetching restaurant...");

        return ResponseEntity.ok(restaurantService.getRestaurantById(restaurantId));
    }

    //List of restaurants
    @PostMapping("/getRestaurantList")
    public ResponseEntity<Page<RestaurantResponseDTO>> getRestaurants(@RequestParam int page, @RequestParam int size)
    {
        return ResponseEntity.ok(restaurantService.getRestaurantList(page, size));
    }

//    @PostMapping("/search")
//    public ResponseEntity<List<RestaurantResponseDTO>> searchRestaurants(@RequestBody RestaurantSearchRequestDTO request)
//    {
//        log.info("Searching restaurants");
//        return ResponseEntity.ok(restaurantService.searchRestaurants(request));
//    }

    //Search restaurants
    @PostMapping("/search")
    public ResponseEntity<List<RestaurantResponseDTO>> searchRestaurant(@RequestBody RestaurantSearchRequestDTO request)
    {
        log.info("Searching restaurants");
        return ResponseEntity.ok(restaurantService.searchRestaurants(request));
    }

    //Restaurant list PDF
    @PostMapping("/downloadRestaurantListPdf")
    public void downloadRestaurantListPdf(HttpServletResponse response) throws Exception
    {
        restaurantService.generateRestaurantListPdf(response);
    }
}
