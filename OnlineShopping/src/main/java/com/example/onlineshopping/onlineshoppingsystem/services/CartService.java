package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.CartDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;

public interface CartService {
    CartDTOResponse getAllCartItems(String userName);

    void addItemToCart(String userName, long productId, int quantity) throws InvalidInputDataException, NotFoundException;

    void editItemInCart(String userName, long productId, int quantity) throws InvalidInputDataException;

    void deleteItemInCart(String userName, long productId) throws InvalidInputDataException;

    CartItem findCartItemByKey(CartItem.CartItemKey key);
}
