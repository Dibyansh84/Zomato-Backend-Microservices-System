package com.auth.service.service;

import com.auth.service.dto.LoginRequestDTO;
import com.auth.service.dto.LoginResponseDTO;
import com.auth.service.dto.RegisterRequestDTO;

public interface AuthService
{
    LoginResponseDTO register(RegisterRequestDTO request);

    LoginResponseDTO login(LoginRequestDTO request);
}
