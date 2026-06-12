package com.restaurant_service.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "onboarding_status")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingStatus
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;
    @Column(name = "status")
    private String status;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "approved_by")
    private String approvedBy;
    @Column(name = "approved_on")
    private LocalDateTime approvedOn;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
}
