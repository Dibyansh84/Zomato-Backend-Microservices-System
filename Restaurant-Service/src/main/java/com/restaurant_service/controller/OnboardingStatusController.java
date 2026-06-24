package com.restaurant_service.controller;

import com.restaurant_service.DTO.OnboardingStatusRequestDTO;
import com.restaurant_service.DTO.OnboardingStatusResponseDTO;
import com.restaurant_service.service.OnboardingStatusService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/onboarding")
@Slf4j
@Validated
public class OnboardingStatusController
{
    @Autowired
    private OnboardingStatusService onboardingStatusService;

    /* Update Onboarding status*/
    @PostMapping("/updateStatus")
    public ResponseEntity<OnboardingStatusResponseDTO> updateStatus(@Valid @RequestBody OnboardingStatusRequestDTO requestDTO)
    {
        log.info("Updating onboarding status");

        OnboardingStatusResponseDTO statusResponseDTO = onboardingStatusService.updateStatus(requestDTO);
        return ResponseEntity.ok(statusResponseDTO);
    }
}
