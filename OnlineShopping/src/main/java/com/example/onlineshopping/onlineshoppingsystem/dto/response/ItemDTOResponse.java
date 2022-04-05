package com.example.onlineshopping.onlineshoppingsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ItemDTOResponse {
    private Long productId;
    private String name;
    private Double price;
    private Integer quantity;
    private Double total;
}
