package com.example.onlineshopping.onlineshoppingsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTOResponse {
    private long userId;
    private long productId;
    private Double score;
    private String comment;
}