package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.CartItemDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.ProductDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping("/add-item-to-cart")
    public ResponseEntity addItemToCart(@PathVariable Long userId, @RequestBody CartItemDTORequest cartItemDTORequest) throws InvalidInputDataException, NotFoundException {
        cartService.addItemToCart(userId, cartItemDTORequest);
        return new ResponseEntity(new SuccessResponse("Add item to cart successfully!"), HttpStatus.OK);
    }

    @PutMapping("/edit-item-in-cart")
    public ResponseEntity editItemInCart(@PathVariable Long userId, @RequestBody CartItemDTORequest cartItemDTORequest) throws Exception {
        cartService.editItemInCart(userId, cartItemDTORequest);
        return new ResponseEntity(new SuccessResponse("Edit item in cart successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/delete-item-in-cart")
    public ResponseEntity deleteItemInCart(@PathVariable Long userId, @PathVariable Long productId) throws NotFoundException, InvalidInputDataException {
        cartService.deleteItemInCart(userId, productId);
        return new ResponseEntity(new SuccessResponse("Delete item in cart successfully!"), HttpStatus.OK);
    }
}
