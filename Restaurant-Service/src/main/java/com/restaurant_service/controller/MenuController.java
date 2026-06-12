package com.restaurant_service.controller;

import com.restaurant_service.DTO.MenuRequestDTO;
import com.restaurant_service.DTO.MenuResponseDTO;
import com.restaurant_service.DTO.RestaurantResponseDTO;
import com.restaurant_service.service.MenuService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/menu")
@Slf4j
@Validated
public class MenuController
{
    @Autowired
    private MenuService menuService;

    @PostMapping("/create")
    public ResponseEntity<MenuResponseDTO> createMenu(@Valid @RequestBody MenuRequestDTO requestDTO)
    {
        log.info("Creating menu");
        return ResponseEntity.ok(menuService.createMenu(requestDTO));
    }

    @PostMapping("/getMenusByRestaurantId")
    public ResponseEntity<List<MenuResponseDTO>> getMenusByRestaurantId(@RequestParam Long restaurantId)
    {
        try {
            log.info("Fetching restaurant");
            List<MenuResponseDTO> menuResponseDTOList = menuService.getMenusByRestaurantId(restaurantId);

            return ResponseEntity.ok(menuResponseDTOList);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException(e);

        }
    }
}
