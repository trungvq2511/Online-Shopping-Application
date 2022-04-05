package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/cart")
public class CartController {
    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @GetMapping("/get-all-cart-items/{userId}")
    public ResponseEntity getAllCartItems(@PathVariable long userId) {
        return new ResponseEntity(new SuccessResponse(cartService.getAllCartItems(userId)), HttpStatus.OK);
    }
}
