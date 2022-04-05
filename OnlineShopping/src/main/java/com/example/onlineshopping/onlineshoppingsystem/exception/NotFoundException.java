package com.example.onlineshopping.onlineshoppingsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class NotFoundException extends Exception {
    public NotFoundException(String message) {
        super(message);
    }
}
