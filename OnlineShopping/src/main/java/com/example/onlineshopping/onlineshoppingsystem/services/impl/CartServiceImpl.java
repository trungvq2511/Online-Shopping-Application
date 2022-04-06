package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.CartItemDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.CartDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.ItemDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.CartItemsRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.ProductRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.UserRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.CartService;
import com.example.onlineshopping.onlineshoppingsystem.services.ProductService;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class CartServiceImpl implements CartService {
    private final UserService userService;
    private final ProductService productService;
    private final CartItemsRepository cartItemsRepository;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;
    private final ModelMapper modelMapper;


    public CartServiceImpl(CartItemsRepository cartItemsRepository,
                           UserRepository userRepository,
                           ModelMapper modelMapper,
                           ProductRepository productRepository,
                           UserService userService,
                           ProductService productService) {
        this.cartItemsRepository = cartItemsRepository;
        this.userRepository = userRepository;
        this.modelMapper = modelMapper;
        this.productRepository = productRepository;
        this.userService = userService;
        this.productService = productService;
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

    @Override
    public void addItemToCart(Long userId, CartItemDTORequest dto) throws InvalidInputDataException {
        saveItemToCart(userId, dto.getProductId(), dto.getQuantity());
    }

    @Override
    public void editItemInCart(Long userId, CartItemDTORequest dto) throws InvalidInputDataException {
        saveItemToCart(userId, dto.getProductId(), dto.getQuantity());
    }

    private void saveItemToCart(Long userId, Long productId, Integer quantity) throws InvalidInputDataException {
        Map<String, String> errors = new HashMap<>();
        User userById = userService.getUserById(userId);
        if (userById != null) {
            errors.put("user", "is not found");
        }
        Product item = productService.getProductById(productId);
        if (item != null) {
            errors.put("product", "is not found");
        }
        if (item.getQuantity() < quantity) {
            errors.put("product quantity", "is not enough");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            cartItemsRepository.save(new CartItem(userById, item, quantity));
        }
    }

    @Override
    public void deleteItemInCart(Long userId, Long productId) throws InvalidInputDataException {
        Map<String, String> errors = new HashMap<>();
        User userById = userService.getUserById(userId);
        if (userById != null) {
            errors.put("user", "is not found");
        }
        Product item = productService.getProductById(productId);
        if (item != null) {
            errors.put("product", "is not found");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            CartItem.CartItemKey key = new CartItem.CartItemKey(userId, productId);
            cartItemsRepository.deleteById(key);
        }
    }

}
