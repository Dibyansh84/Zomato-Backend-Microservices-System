package com.restaurant_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name="restaurants")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Restaurant
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "restaurant_name")
    private String restaurantName;
    @Column(name = "owner_name")
    private String ownerName;
    @Column(name = "email")
    private String email;
    @Column(name = "phone")
    private String phone;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "cuisine_type")
    private String cuisineType;
    @Column(name = "rating")
    private Double rating;
    @Column(name = "active")
    private Boolean active;
    @Column(name = "onboarding_completed")
    private Boolean onboardingCompleted;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    /* The `@PrePersist` annotation is used to mark a method in
    an entity class that should be executed just before the entity is persisted for the first time. */
    /*In simple words, @PrePersist annotation is used to execute a method automatically before saving data
    into the DB for the first time.
     */

    @PrePersist
    public void onCreate()
    {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();

        if(rating == null)
        {
            rating = 0.0;
        }
        if(active == null)
        {
            active = true;
        }
        if(onboardingCompleted == null)
        {
            onboardingCompleted = false;
        }
    }

    /* The @PreUpdate annotation is used to mark a method in an entity class that should be
    * executed just before an entity is updated in the DB.*/
    /* In simple words, @PreUpdate annotation is used to run a method automatically just before
    * updating data in the database. */

    @PreUpdate
    public void onUpdate()
    {
        updatedOn = LocalDateTime.now();
    }
}
