package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.CartItemDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.request.ProductDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.services.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

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
    public ResponseEntity addItemToCart(@RequestParam Long productId,@RequestParam Integer quantity) throws InvalidInputDataException, NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        cartService.addItemToCart(username, productId, quantity);
        return new ResponseEntity(new SuccessResponse("Add item to cart successfully!"), HttpStatus.OK);
    }

    @PutMapping("/edit-item-in-cart")
    public ResponseEntity editItemInCart(@RequestParam Long productId,@RequestBody CartItemDTORequest dto) throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        cartService.editItemInCart(username, productId, dto);
        return new ResponseEntity(new SuccessResponse("Edit item in cart successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/delete-item-in-cart")
    public ResponseEntity deleteItemInCart( @RequestParam Long productId) throws NotFoundException, InvalidInputDataException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        cartService.deleteItemInCart(username, productId);
        return new ResponseEntity(new SuccessResponse("Delete item in cart successfully!"), HttpStatus.OK);
    }
}
