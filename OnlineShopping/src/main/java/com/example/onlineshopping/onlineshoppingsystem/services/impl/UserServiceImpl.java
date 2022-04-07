package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.UserDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.UserDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.EnumRole;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.Role;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.RoleRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.UserRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ModelMapper modelMapper;

    public UserServiceImpl(ModelMapper modelMapper,
                           PasswordEncoder passwordEncoder,
                           RoleRepository roleRepository,
                           UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.roleRepository = roleRepository;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @Override
    public void saveNewUser(UserDTORequest userRequestDTO) {
        User user = modelMapper.map(userRequestDTO, User.class);

        //role user
        Role roleUser = roleRepository.findRoleByName(EnumRole.ROLE_USER.toString());
        user.setRole(new ArrayList<>() {{
            add(roleUser);
        }});
        user.setVerified(false);
        //encode password
        user.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
        userRepository.save(user);
    }

    @Override
    public List<UserDTOResponse> getAllUsers() {
        List<User> all = userRepository.findAll();
        List<UserDTOResponse> userDTOResponses = new ArrayList<>();

        all.forEach(user -> {
            UserDTOResponse map = modelMapper.map(user, UserDTOResponse.class);
            userDTOResponses.add(map);
        });

        return userDTOResponses;
    }

    @Override
    public User getUserById(long userId) {
        Optional<User> byId = userRepository.findById(userId);
        return byId.isPresent() ? byId.get() : null;
    }

    @Override
    public UserDTOResponse getUserByUsername(String username) {
        User userByEmail = userRepository.findUserByEmail(username);
        return modelMapper.map(userByEmail, UserDTOResponse.class);
    }

    @Override
    public UserDTOResponse getUserInfo(String username) {
        return modelMapper.map(userRepository.findUserByEmail(username), UserDTOResponse.class);
    }

    @Override
    public void editUserInfo(String username, UserDTORequest userDTORequest) throws NotFoundException {
        User userByEmail = userRepository.findUserByEmail(username);
        if (userByEmail == null) {
            throw new NotFoundException("User is not found!");
        }
        userDTORequest.setPassword(userByEmail.getPassword());

        modelMapper.map(userDTORequest, userByEmail);
        userRepository.save(userByEmail);
    }

    @Override
    public void deleteUser(long userId) throws NotFoundException {
        User userById = getUserById(userId);
        if (userById == null) {
            throw new NotFoundException("User is not found!");
        }
        userRepository.delete(userById);
    }

    @Override
    public User checkExistsUserByUsername(String username) {
        Optional<User> user = userRepository.existsUserByEmail(username);
        return user.isPresent() ? userRepository.findUserByEmail(username) : null;
    }


}
