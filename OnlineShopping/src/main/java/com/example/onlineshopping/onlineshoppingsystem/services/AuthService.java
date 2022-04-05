package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.AccessTokenDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserRequestDTO;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.RefreshTokenDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidTokenException;

public interface AuthService {
    void signUp(UserRequestDTO userRequestDTO) throws InvalidInputDataException;

    RefreshTokenDTOResponse refreshToken(AccessTokenDTORequest accessTokenDTORequest) throws InvalidTokenException;
}
