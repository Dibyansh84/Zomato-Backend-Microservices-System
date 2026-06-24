package com.restaurant_service.DTO;
import com.restaurant_service.enums.OnboardingStatusEnum;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class OnboardingStatusRequestDTO
{
    @NotNull
    private Long restaurantId;
    @NotNull
    private OnboardingStatusEnum status;
    private String remarks;
    private String approvedBy;
}
