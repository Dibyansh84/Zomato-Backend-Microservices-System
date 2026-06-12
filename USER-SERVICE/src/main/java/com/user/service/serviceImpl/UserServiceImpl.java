package com.user.service.serviceImpl;

import com.user.service.dto.UserRequestDTO;
import com.user.service.dto.UserResponseDTO;
import com.user.service.entity.User;
import com.user.service.repository.UserCustomRepository;
import com.user.service.repository.UserJpaRepository;
import com.user.service.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserServiceImpl implements UserService
{
    // Injecting JPA repository
    @Autowired
    private UserJpaRepository userJpaRepository;

    // Injecting custom repository
    @Autowired
    private UserCustomRepository userCustomRepository;

    @Override
    public UserResponseDTO createUser(UserRequestDTO request)
    {
        // Log entry for tracking user creation process
        log.info("Creating user");

        //Create a new User entity object
        User user = new User();

        //Get the data from DTO and set to the entity
        //UserRequestDTO →  User entity
        // Set user's name received from request DTO into entity
        user.setName(request.getName());
        //Set user's email received from request DTO into entity
        user.setEmail(request.getEmail());
        //Set user's phone number from request DTO into entity
        user.setPhone(request.getPhone());

        //Set user's address from request DTO into entity
        user.setAddress(request.getAddress());

        // Save user entity into database using JPA Repository's save() method
        //After saving, database-generated values like ID will be available
        User savedUser = userJpaRepository.save(user);

        //Create response DTO object
        UserResponseDTO response = new UserResponseDTO();

        //Get the save data from the entity and set it to the UserResponseDTO
        //User entity → UserResponseDTO
        //Set generated user ID into UserResponseDTO
        response.setId(savedUser.getId());

        //Set saved user's name into UserResponseDTO
        response.setName(savedUser.getName());

        //Set saved user's email into UserResponseDTO
        response.setEmail(savedUser.getEmail());

        //Set saved user's phone number into UserResponseDTO
        response.setPhone(savedUser.getPhone());

        //Set saved user's address into UserResponseDTO
        response.setAddress(savedUser.getAddress());

        //Return final UserResponseDTO to controller/client
        return response;
    }

    @Override
    public UserResponseDTO getUserByUserId(Long userId)
    {
        //Fetching user data from database
        User user = userCustomRepository.getUserByUserId(userId);

        //Create response DTO object
        UserResponseDTO responseDTO = new UserResponseDTO();

        //Setting user details into DTO
        responseDTO.setId(user.getId());
        responseDTO.setName(user.getName());
        responseDTO.setEmail(user.getEmail());
        responseDTO.setPhone(user.getPhone());
        responseDTO.setAddress(user.getAddress());

        //Returning response
        return responseDTO;
    }

    @Override
    public List<UserResponseDTO> getUsers() {
        //Fetch user list from the DB
        List<User> users = userCustomRepository.getUsers();

        //Create response list
        List<UserResponseDTO> responseList = new ArrayList<>();

        //Looping through users list
        for (User user : users) {
            //Creating DTO object
            UserResponseDTO dto = new UserResponseDTO();

            //Mapping entity data to DTO
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setEmail(user.getEmail());
            dto.setPhone(user.getPhone());
            dto.setAddress(user.getAddress());

            //Adding DTO to response list
            responseList.add(dto);
        }

        //Returning final response
        return responseList;
    }
}
