package com.restaurant_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "food_items")
@AllArgsConstructor
@NoArgsConstructor
public class FoodItem
{
    //IDENTITY = "Database handles it, gives ID after saving"
    //AUTO = "Hibernate figures it out automatically"
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Menu id cannot be null")
    @Column(name = "menu_id", nullable = false)
    private Long menuId;
    @Column(name = "item_name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "category")
    private String category;
    @Column(name = "is_veg")
    private Boolean isVeg;
    @Column(name = "price")
    private Double price;
    @Column(name = "available")
    private Boolean available;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;


    @PrePersist
    public void onCreate()
    {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();

        if(available == null)
        {
            available = true;
        }
        if(rating == null)
        {
            rating = 0.0;
        }
    }

    @PreUpdate
    public void onUpdate()
    {
        updatedOn = LocalDateTime.now();
    }

}
