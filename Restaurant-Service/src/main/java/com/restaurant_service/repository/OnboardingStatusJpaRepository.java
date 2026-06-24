package com.restaurant_service.repository;

import com.restaurant_service.entity.OnboardingStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnboardingStatusJpaRepository extends JpaRepository<OnboardingStatus, Long>
{
    boolean existsById(Long restaurantId);
}
