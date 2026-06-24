package com.restaurant_service.repository;

import com.restaurant_service.entity.Menu;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuJpaRepository extends JpaRepository<Menu, Long>
{
    boolean existsByRestaurantIdAndMenuNameIgnoreCase(Long restaurantId, String name);

    List<Menu> findByRestaurantId(Long restaurantId);
}
