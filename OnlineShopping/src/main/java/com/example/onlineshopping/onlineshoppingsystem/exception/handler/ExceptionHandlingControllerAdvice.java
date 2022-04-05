package com.example.onlineshopping.onlineshoppingsystem.exception.handler;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.ErrorResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidFileTypeException;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidTokenException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlingControllerAdvice {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity handleNotFoundException(NotFoundException exception) {
        return new ResponseEntity(new ErrorResponse(exception.getMessage()), HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity handleInvalidTokenException(InvalidTokenException exception) {
        return new ResponseEntity(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidFileTypeException.class)
    public ResponseEntity handleInvalidFileTypeException(InvalidFileTypeException exception) {
        return new ResponseEntity(new ErrorResponse(exception.getMessage()), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(InvalidInputDataException.class)
    public ResponseEntity handleInvalidInputDataException(InvalidInputDataException exception) {
        StringBuilder errors = new StringBuilder();
        exception.getErrors().forEach((field, message) -> errors.append(field).append(" ").append(message).append(" and "));
        String substring = errors.substring(0, errors.length() - 5);

        return new ResponseEntity(new ErrorResponse(substring), HttpStatus.BAD_REQUEST);
    }
}
