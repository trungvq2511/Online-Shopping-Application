package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.CartDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.ItemDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.repositories.CartItemsRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.UserRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartServiceImpl implements CartService {
    private final CartItemsRepository cartItemsRepository;
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;


    public CartServiceImpl(CartItemsRepository cartItemsRepository,
                           UserRepository userRepository, ModelMapper modelMapper) {
        this.cartItemsRepository = cartItemsRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public CartDTOResponse getAllCartItems(long userId) {
        CartDTOResponse cartDTOResponse = new CartDTOResponse();

        User user = userRepository.getById(userId);
        List<CartItem> cartItemByUser = cartItemsRepository.findCartItemByUser(user);
        cartItemByUser.forEach(cartItem -> {
            ItemDTOResponse item = modelMapper.map(cartItem.getProduct(), ItemDTOResponse.class);
            item.setQuantity(cartItem.getQuantity());
            item.setTotal(cartItem.getProduct().getPrice() * item.getQuantity());
            cartDTOResponse.getItems().add(item);
        });

        //total cart
        double total = 0;
        for (ItemDTOResponse itemDTOResponse : cartDTOResponse.getItems()) {
            total = total + itemDTOResponse.getTotal();
        }

        cartDTOResponse.setTotal(total);
        return cartDTOResponse;
    }
}
