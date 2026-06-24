package com.restaurant_service.DTO;

import com.restaurant_service.enums.OnboardingStatusEnum;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class OnboardingStatusResponseDTO
{
   private Long id;
   private Long restaurantId;
   private OnboardingStatusEnum status;
   private String remarks;
   private String approvedBy;
   private LocalDateTime approvedOn;
   private LocalDateTime createdOn;
}
