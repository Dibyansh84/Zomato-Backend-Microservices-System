package com.auth.service.controller;

import com.auth.service.dto.LoginRequestDTO;
import com.auth.service.dto.LoginResponseDTO;
import com.auth.service.dto.RegisterRequestDTO;
import com.auth.service.service.AuthService;
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
@RequestMapping("/api/auth")
@Slf4j
@Validated
public class AuthController
{
    @Autowired
    private AuthService authService;

    /* Register API*/

    @PostMapping("/register")
    public ResponseEntity<LoginResponseDTO> register(@Valid @RequestBody RegisterRequestDTO request)
    {
        return ResponseEntity.ok(
                authService.register(request)
        );
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@Valid @RequestBody LoginRequestDTO request)
    {
        return ResponseEntity.ok(authService.login(request));
    }
}
