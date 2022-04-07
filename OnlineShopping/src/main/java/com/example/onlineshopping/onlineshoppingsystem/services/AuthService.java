package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.RefreshTokenDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.RefreshTokenDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidTokenException;

public interface AuthService {
    void signUp(UserDTORequest userRequestDTO) throws InvalidInputDataException;

    RefreshTokenDTOResponse refreshToken(RefreshTokenDTORequest accessTokenDTORequest) throws InvalidTokenException;

    void verifyEmail(String token) throws InvalidTokenException;
}
