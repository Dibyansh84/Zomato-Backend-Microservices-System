package com.restaurant_service.repository;

import com.restaurant_service.entity.Restaurant;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface RestaurantJpaRepository extends JpaRepository<Restaurant, Long>
{
    //For checking duplicate email
    boolean existsByEmail(String email);


    @Modifying
    @Transactional
    @Query("""
            UPDATE Restaurant r
            SET r.onboardingCompleted = :status
            WHERE r.id = :restaurantId
            """)
    void updateOnboardingStatus(@Param("restaurantId") Long restaurantId, @Param("status") Boolean status);
}
