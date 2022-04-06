package com.example.onlineshopping.onlineshoppingsystem.dto.request;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class CartItemDTORequest {
    private Long productId;
    private Integer quantity;
}
