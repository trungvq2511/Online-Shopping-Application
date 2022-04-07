package com.example.onlineshopping.onlineshoppingsystem.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RatingDTORequest {
    private long productId;
    private Double score;
    private String comment;
}