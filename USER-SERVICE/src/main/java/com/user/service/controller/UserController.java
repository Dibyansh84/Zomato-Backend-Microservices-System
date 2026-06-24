package com.user.service.controller;

import com.user.service.dto.UserRequestDTO;
import com.user.service.dto.UserResponseDTO;
import com.user.service.entity.User;
import com.user.service.service.UserService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
@Validated
public class UserController
{
    @Autowired
    private UserService userService;

    //Create a user
    @PostMapping("/create")
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserRequestDTO request)
    {
        return ResponseEntity.ok(userService.createUser(request));
    }

    @PostMapping("/getUserByUserId")
    public ResponseEntity<UserResponseDTO> getUserByUserId(@RequestParam Long userId)
    {
        return ResponseEntity.ok(userService.getUserByUserId(userId));
    }

    //List of users
    @PostMapping("/getUsers")
    public ResponseEntity<List<UserResponseDTO>> getUsers()
    {
        return ResponseEntity.ok(userService.getUsers());
    }
}
