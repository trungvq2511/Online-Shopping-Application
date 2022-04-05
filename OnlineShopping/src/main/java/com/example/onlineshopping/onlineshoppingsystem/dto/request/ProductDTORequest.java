package com.example.onlineshopping.onlineshoppingsystem.dto.request;

import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

public class ProductDTORequest {
    private String name;
    private Double price;
    private Integer quantity;
    private String manufacturedFactory;
    private String madeIn;
    private List<Long> categories;
}
