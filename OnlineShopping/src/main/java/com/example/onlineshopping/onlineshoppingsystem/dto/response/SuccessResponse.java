package com.example.onlineshopping.onlineshoppingsystem.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class SuccessResponse {
    private Boolean result = true;
    private Object response;

    public SuccessResponse(Object response) {
        this.response = response;
    }
}
