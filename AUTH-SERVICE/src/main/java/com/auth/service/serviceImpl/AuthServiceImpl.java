package com.auth.service.serviceImpl;

import com.auth.service.dto.*;
import com.auth.service.entity.AuthUser;
import com.auth.service.feign.UserClient;
import com.auth.service.repository.AuthJpaRepository;
import com.auth.service.service.AuthService;
import com.auth.service.utility.JwtUtil;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Service
@Slf4j
public class AuthServiceImpl implements AuthService
{


    // Inject Feign Client for User Service communication
    @Autowired
    private UserClient userClient;

    // Inject JPA Repository for Auth User database operations
    @Autowired
    private AuthJpaRepository authJpaRepository;

    // Inject BCryptPasswordEncoder for password encryption
    @Autowired
    private BCryptPasswordEncoder encoder;

    // Inject JwtUtil class for JWT token generation
    @Autowired
    private JwtUtil jwtUtil;

    @Override
    public LoginResponseDTO register(RegisterRequestDTO request)
    {
        // Log registration process start
        log.info("User registration process started");

        /*
         * Create User in User Service
         */

        //Create UserRequestDTO object
        UserRequestDTO userRequest = new UserRequestDTO();
        // Set user's name from request
        userRequest.setName(request.getName());
        // Set user's email from request
        userRequest.setEmail(request.getEmail());
        // Set phone number from request
        userRequest.setPhone(request.getPhone());
        // Set user address from request
        userRequest.setAddress(request.getAddress());

        // Call User Service using Feign Client to create user details
        UserResponseDTO userResponse = userClient.createUser(userRequest);

        /*
        * Save authentication details
        */
        //Create AuthUser entity object
        AuthUser authUser = new AuthUser();

        //Set generated userId received from User Service
        authUser.setUserId(userResponse.getId());

        //Set username
        authUser.setUsername(request.getUsername());

        //Encrypt password using BCrypt before saving
        authUser.setPassword(encoder.encode(request.getPassword()));

        //Assign default role to user
        authUser.setRole("CUSTOMER");

        //Save authentication details into database
        authJpaRepository.save(authUser);

        //Generate JWT token using username
        String token = jwtUtil.generateToken(authUser.getUsername());

        //Return Login Response DTO
        return new LoginResponseDTO(token, "Registration Successful",
                authUser.getUsername(), authUser.getRole());
    }

    @Override
    public LoginResponseDTO login(LoginRequestDTO request)
    {
         AuthUser authUser = authJpaRepository.findByUsername(request.getUsername())
               .orElseThrow(() -> new RuntimeException("Invalid Username"));

         /* Validate Password*/
//        if(!encoder.matches(request.getPassword(), authUser.getPassword()))
//        {
//            throw new RuntimeException("Invalid Password");
//        }
//
//        String token = jwtUtil.generateToken(authUser.getUsername());
//        return new LoginResponseDTO(token, "Login Successful", authUser.getUsername(), authUser.getRole());

        if(!encoder.matches(request.getPassword(), authUser.getPassword()))
        {
            throw new RuntimeException("Invalid Password");
        }
        String token = jwtUtil.generateToken(authUser.getUsername());
        return new LoginResponseDTO(token, "Login Successful", authUser.getUsername(), authUser.getRole());
    }
}
