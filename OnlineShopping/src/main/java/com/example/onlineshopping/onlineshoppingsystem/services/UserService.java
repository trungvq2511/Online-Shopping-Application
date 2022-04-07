package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserRequestDTO;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.UserDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;

import java.util.List;

public interface UserService {
    void saveNewUser(UserRequestDTO userRequestDTO);

    List<UserDTOResponse> getAllUsers();
User getUserById(long userId);
    UserDTOResponse getUserByUsername(String username);


    UserDTOResponse getUserInfo(String username);
}
