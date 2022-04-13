package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.services.RoleService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/role")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @GetMapping("/get/all-role")
    public ResponseEntity getAllRole() {
        return new ResponseEntity(new SuccessResponse(roleService.getAllRole()), HttpStatus.OK);
    }
}
