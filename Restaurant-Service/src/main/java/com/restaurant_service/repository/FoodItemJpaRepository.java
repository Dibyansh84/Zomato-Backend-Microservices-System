package com.restaurant_service.repository;

import com.restaurant_service.entity.FoodItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FoodItemJpaRepository extends JpaRepository<FoodItem, Long>
{
    List<FoodItem> findByMenuId(Long menuId);
}
