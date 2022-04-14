package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.UserDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

public interface UserService {
    void saveNewUser(UserDTORequest userRequestDTO);

    List<UserDTOResponse> getAllUsers(int pageIndex, int pageSize);

    User getUserById(long userId);

    UserDTOResponse getUserByUsername(String username);

    UserDTOResponse getUserInfo(String username);

    void editUserInfo(String username, UserDTORequest userDTORequest) throws NotFoundException;

    void deleteUser(long userId) throws NotFoundException;

    User checkExistsUserByUsername(String username);
}
