package com.example.onlineshopping.onlineshoppingsystem.repositories;

import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem.CartItemKey;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItem, CartItemKey> {
    List<CartItem> findCartItemByUser(User user);
    List<CartItem> findAllByUser_UserId(Long userId);

    @Transactional
    void removeAllByUserUserId(Long userId);

}
