package com.restaurant_service.service;

import com.restaurant_service.DTO.OnboardingStatusRequestDTO;
import com.restaurant_service.DTO.OnboardingStatusResponseDTO;

import java.util.List;

public interface OnboardingStatusService
{
    OnboardingStatusResponseDTO updateStatus(OnboardingStatusRequestDTO requestDTO);

    List<OnboardingStatusResponseDTO> getOnboardingStatusHistory(Long restaurantId);
}
