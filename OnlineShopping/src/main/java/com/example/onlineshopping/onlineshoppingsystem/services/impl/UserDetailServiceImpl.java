package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.repositories.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserDetailServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    public UserDetailServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findUserByEmail(email);
        if (user == null) {
            throw new UsernameNotFoundException("Email " + email + "is not found!");
        }

        //authorities
        List<GrantedAuthority> authorities = new ArrayList<>();
        user.getRole().forEach(role -> {
            authorities.add(new SimpleGrantedAuthority(role.getName().toString()));
        });
        org.springframework.security.core.userdetails.User returnUser = new org.springframework.security.core.userdetails.User(user.getEmail(),
                user.getPassword(),
                authorities);
        System.out.println("USER FOUND\n" + returnUser.toString());
        return returnUser;
    }
}
