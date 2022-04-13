package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.RoleDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.Role;
import com.example.onlineshopping.onlineshoppingsystem.repositories.RoleRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.RoleService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoleServiceImpl implements RoleService {
    private final ModelMapper modelMapper;
    private final RoleRepository roleRepository;

    public RoleServiceImpl(ModelMapper modelMapper,
                           RoleRepository roleRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleDTOResponse> getAllRole() {
        List<Role> all = roleRepository.findAll();
        List<RoleDTOResponse> roleDTOResponseList = all.stream().map(role -> modelMapper.map(role, RoleDTOResponse.class)).collect(Collectors.toList());
        return roleDTOResponseList;
    }
}
