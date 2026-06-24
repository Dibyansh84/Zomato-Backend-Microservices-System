package com.restaurant_service.repository;

import com.restaurant_service.entity.RestaurantDocument;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantDocumentJpaRepository extends JpaRepository<RestaurantDocument, Long>
{
}
