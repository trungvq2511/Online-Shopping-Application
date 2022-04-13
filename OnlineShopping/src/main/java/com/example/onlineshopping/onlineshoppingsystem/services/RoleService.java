package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.RoleDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface RoleService {
    List<RoleDTOResponse> getAllRole();
}
