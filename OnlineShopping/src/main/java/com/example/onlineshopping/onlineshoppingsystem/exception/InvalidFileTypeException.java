package com.example.onlineshopping.onlineshoppingsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class InvalidFileTypeException extends Exception {
    public InvalidFileTypeException(String message) {
        super(message);
    }
}
