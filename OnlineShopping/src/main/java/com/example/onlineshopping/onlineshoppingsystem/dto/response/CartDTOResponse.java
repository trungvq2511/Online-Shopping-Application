package com.example.onlineshopping.onlineshoppingsystem.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CartDTOResponse {
    private List<ItemDTOResponse> items = new ArrayList<>();
    private Double total;
}
