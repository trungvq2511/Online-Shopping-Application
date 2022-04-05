package com.example.onlineshopping.onlineshoppingsystem.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class InvalidInputDataException extends Exception {
    private Map<String, String> errors;
}
