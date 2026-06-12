package com.auth.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginResponseDTO
{
    private String token;

    private String message;

    private String username;    // Optional: return username

    private String role;        // Optional: return user role
}
