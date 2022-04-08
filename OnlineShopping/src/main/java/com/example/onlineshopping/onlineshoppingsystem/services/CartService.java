package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.CartItemDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.ProductDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.CartDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;

public interface CartService {
    CartDTOResponse getAllCartItems(String username) throws NotFoundException;
    void addItemToCart(String username, CartItemDTORequest cartItemDTORequest) throws InvalidInputDataException, NotFoundException;
    void editItemInCart(String username,  CartItemDTORequest cartItemDTORequest) throws InvalidInputDataException;
    void deleteItemInCart(String username, Long productId) throws InvalidInputDataException;
}
