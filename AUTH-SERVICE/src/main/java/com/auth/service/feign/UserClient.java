package com.auth.service.feign;

import com.auth.service.dto.UserRequestDTO;
import com.auth.service.dto.UserResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "USER-SERVICE")
public interface UserClient
{

    @PostMapping("/api/users/create")
    UserResponseDTO createUser(@RequestBody UserRequestDTO request);
}
