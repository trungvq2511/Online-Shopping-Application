package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.RefreshTokenDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserRequestDTO;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidTokenException;
import com.example.onlineshopping.onlineshoppingsystem.services.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/signUp")
    public ResponseEntity signUp(@RequestBody UserRequestDTO userRequestDTO) throws InvalidInputDataException {
        authService.signUp(userRequestDTO);
        return new ResponseEntity(new SuccessResponse("Register successfully!"), HttpStatus.OK);
    }

    @GetMapping("/token/refresh")
    public ResponseEntity refreshToken(@RequestBody RefreshTokenDTORequest accessToken) throws InvalidTokenException {
        return new ResponseEntity(new SuccessResponse(authService.refreshToken(accessToken)), HttpStatus.OK);
    }

}