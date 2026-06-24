package com.restaurant_service.serviceImpl;

import com.restaurant_service.DTO.MenuRequestDTO;
import com.restaurant_service.DTO.MenuResponseDTO;
import com.restaurant_service.entity.Menu;
import com.restaurant_service.exception.DuplicateResourceException;
import com.restaurant_service.exception.ResourceNotFoundException;
import com.restaurant_service.repository.MenuCustomRepository;
import com.restaurant_service.repository.MenuJpaRepository;
import com.restaurant_service.repository.RestaurantJpaRepository;
import com.restaurant_service.service.MenuService;
import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Slf4j
public class MenuServiceImpl implements MenuService
{
    @Autowired
    private RestaurantJpaRepository restaurantJpaRepository;

    @Autowired
    private MenuJpaRepository menuJpaRepository;

    @Autowired
    private MenuCustomRepository menuCustomRepository;

    @Override
    @Transactional
    @CacheEvict(value = "menuCache", allEntries = true)
    public MenuResponseDTO createMenu(MenuRequestDTO request)
    {
        log.info("Creating menu for restaurantId : {}", request.getRestaurantId());

        //Check restaurant existence
        boolean restaurantExists = restaurantJpaRepository.existsById(request.getRestaurantId());

        //Validate restaurant
        if(!restaurantJpaRepository.existsById(request.getRestaurantId()))
        {
            throw new ResourceNotFoundException("Restaurant not found with id: "+request.getRestaurantId());
        }

        //Duplicate menu check
        boolean menuExists = menuJpaRepository.existsByRestaurantIdAndMenuNameIgnoreCase(
                request.getRestaurantId(), request.getMenuName().trim()
        );

        if(menuExists)
        {
            throw new DuplicateResourceException(" Menu already exists with name: "+request.getMenuName());
        }

        // Create entity
        Menu menu = new Menu();

        menu.setRestaurantId(request.getRestaurantId());
        menu.setMenuName(request.getMenuName().trim());
        menu.setActive(true);
        menu.setCreatedOn(LocalDateTime.now());

        //Save
        BeanUtils.copyProperties(request, menu);
        Menu savedMenu = menuJpaRepository.save(menu);
        log.info("Menu created successfully with id : {}", savedMenu.getId());

        // Prepare response
        MenuResponseDTO response = new MenuResponseDTO();
        BeanUtils.copyProperties(savedMenu, response);
        return response;
    }

    @Override
    @Cacheable(value = "menuCache", key = "#restaurantId")
    public List<MenuResponseDTO> getMenusByRestaurantId(Long restaurantId)
    {
        log.info(
                "Fetching menus"
        );

        return menuCustomRepository
                .getMenusByRestaurantId(
                        restaurantId
                );

    }
}
