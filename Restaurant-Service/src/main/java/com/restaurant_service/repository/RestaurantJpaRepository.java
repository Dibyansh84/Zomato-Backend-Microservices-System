package com.restaurant_service.repository;

import com.restaurant_service.entity.Restaurant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long>
{
    //For checking duplicate email
    boolean existsByEmail(String email);
}
