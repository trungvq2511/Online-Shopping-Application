package com.example.onlineshopping.onlineshoppingsystem.repositories;

import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem.CartItemKey;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CartItemsRepository extends JpaRepository<CartItem, CartItemKey> {
    List<CartItem> findCartItemByUser(User user);
}
