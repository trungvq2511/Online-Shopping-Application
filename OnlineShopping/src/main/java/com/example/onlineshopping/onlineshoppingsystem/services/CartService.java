package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.CartDTOResponse;

public interface CartService {
    CartDTOResponse getAllCartItems(long userId);
}
