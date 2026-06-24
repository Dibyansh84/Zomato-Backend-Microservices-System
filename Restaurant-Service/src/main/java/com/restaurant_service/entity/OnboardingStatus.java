package com.restaurant_service.entity;

import com.restaurant_service.enums.OnboardingStatusEnum;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "onboarding_status")
@SequenceGenerator(name = "onboarding_status_id_seq", sequenceName = "onboarding_status_id_seq",
allocationSize = 1)
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OnboardingStatus
{
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "onboarding_status_id_seq")
    private Long id;

    @Column(name = "restaurant_id")
    private Long restaurantId;

    /*
     * PENDING
     * UNDER_REVIEW
     * APPROVED
     * REJECTED
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private OnboardingStatusEnum status;
    @Column(name = "remarks")
    private String remarks;
    @Column(name = "approved_by")
    private String approvedBy;
    @Column(name = "approved_on")
    private LocalDateTime approvedOn;
    @Column(name = "created_on")
    private LocalDateTime createdOn;
    @Column(name = "updated_on")
    private LocalDateTime updatedOn;

    @PrePersist
    public void onCreate()
    {
        createdOn = LocalDateTime.now();
        updatedOn = LocalDateTime.now();

        if(status == null)
        {
//            status = "PENDING";
            status = OnboardingStatusEnum.PENDING;
        }
    }

    @PreUpdate
    public void updateNow()
    {
        updatedOn = LocalDateTime.now();
    }
}
