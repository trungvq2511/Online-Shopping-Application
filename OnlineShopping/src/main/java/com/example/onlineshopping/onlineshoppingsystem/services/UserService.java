package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserRequestDTO;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.UserDTOResponse;

import java.util.List;

public interface UserService {
    void saveNewUser(UserRequestDTO userRequestDTO);

    List<UserDTOResponse> getAllUsers();

    UserDTOResponse getUserByUsername(String username);


}
