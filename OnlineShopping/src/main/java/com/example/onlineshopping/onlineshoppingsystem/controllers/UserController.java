package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/get/get-all-users")
    public ResponseEntity getAllUsers(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return new ResponseEntity(new SuccessResponse(userService.getAllUsers(pageIndex,pageSize)), HttpStatus.OK);
    }

    @GetMapping("/get/get-user-info")
    public ResponseEntity getUserInfo() {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return new ResponseEntity(new SuccessResponse(userService.getUserInfo(username)), HttpStatus.OK);
    }

    @PutMapping("/manage/edit-user-info")
    public ResponseEntity editUserInfo(@RequestBody UserDTORequest userDTORequest) throws NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        userService.editUserInfo(username, userDTORequest);
        return new ResponseEntity(new SuccessResponse("Edit user successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/manage/delete-user/{userId}")
    public ResponseEntity deleteUser(@PathVariable long userId) throws NotFoundException {
        userService.deleteUser(userId);
        return new ResponseEntity(new SuccessResponse("Delete user successfully!"), HttpStatus.OK);

    }
}
