package com.restaurant_service.service;

import com.restaurant_service.DTO.OnboardingStatusRequestDTO;
import com.restaurant_service.DTO.OnboardingStatusResponseDTO;

public interface OnboardingStatusService
{
    OnboardingStatusResponseDTO updateStatus(OnboardingStatusRequestDTO requestDTO);
}
