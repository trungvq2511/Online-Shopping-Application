package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/get-all-users")
    public ResponseEntity getAllUsers() {
        return new ResponseEntity(new SuccessResponse(userService.getAllUsers()), HttpStatus.OK);
    }

    @GetMapping("/get/get-user-info")
    public ResponseEntity getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return new ResponseEntity(new SuccessResponse(userService.getUserInfo(username)),HttpStatus.OK);
    }
}
