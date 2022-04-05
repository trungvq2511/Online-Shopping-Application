package com.example.onlineshopping.onlineshoppingsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class ErrorResponse {
    private Boolean result = false;
    private Object response;

    public ErrorResponse(String response) {
        this.response = response;
    }
}
