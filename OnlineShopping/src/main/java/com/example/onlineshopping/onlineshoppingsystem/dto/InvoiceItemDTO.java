package com.example.onlineshopping.onlineshoppingsystem.dto;

import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class InvoiceItemDTO {
    private Product item;
    private Integer quantity;
    private Double price;
    private Double totalInCart;
}
