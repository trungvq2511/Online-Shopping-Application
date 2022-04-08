package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.CartItemDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.ProductDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.CartDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;

public interface CartService {
    CartDTOResponse getAllCartItems(long userId);
    void addItemToCart(Long userID, CartItemDTORequest cartItemDTORequest) throws InvalidInputDataException, NotFoundException;
    void editItemInCart(Long userId,  CartItemDTORequest cartItemDTORequest) throws InvalidInputDataException;

    void deleteItemInCart(Long userId, Long productId) throws InvalidInputDataException;
}
