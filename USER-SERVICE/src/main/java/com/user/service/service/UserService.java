package com.user.service.service;


import com.user.service.dto.UserRequestDTO;
import com.user.service.dto.UserResponseDTO;
import org.jspecify.annotations.Nullable;

import java.util.List;

public interface UserService
{
    UserResponseDTO createUser(UserRequestDTO request);

    UserResponseDTO getUserByUserId(Long userId);

    List<UserResponseDTO> getUsers();
}
