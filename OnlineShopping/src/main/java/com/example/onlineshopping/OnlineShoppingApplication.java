package com.example.onlineshopping;

import com.example.onlineshopping.onlineshoppingsystem.entities.user.Role;
import com.example.onlineshopping.onlineshoppingsystem.repositories.RoleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class OnlineShoppingApplication {

    public static void main(String[] args) {
        SpringApplication.run(OnlineShoppingApplication.class, args);
    }

    @Bean
    CommandLineRunner run(RoleRepository roleRepository) {
        return args -> {
            if (!roleRepository.existsByName("ROLE_USER")) {
                roleRepository.save(new Role("ROLE_USER"));
            }
            if (!roleRepository.existsByName("ROLE_ADMIN")) {
                roleRepository.save(new Role("ROLE_ADMIN"));
            }
        };
    }
}
