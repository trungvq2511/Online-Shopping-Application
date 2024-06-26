package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.CartDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.ItemDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.CartItemsRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.ProductRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.UserRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.CartService;
import com.example.onlineshopping.onlineshoppingsystem.services.ProductService;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    public CartDTOResponse getAllCartItems(String userName) {
        CartDTOResponse cartDTOResponse = new CartDTOResponse();
        User userByEmail = userRepository.findUserByEmail(userName);
        List<CartItem> cartItemByUser = cartItemsRepository.findCartItemByUser(userByEmail);
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

    @Transactional
    @Override
    public void addItemToCart(String userName, long productId, int quantity) throws InvalidInputDataException, NotFoundException {
        Map<String, String> errors = new HashMap<>();
        User userByEmail = userRepository.findUserByEmail(userName);
        Product product = productRepository.findAllByProductId(productId);
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product with id = " + productId + " is not found!");
        }
        if (product.getQuantity() < quantity) {
            errors.put("product quantity", "is not enough");
        }
        if (findCartItemByKey(new CartItem.CartItemKey(userByEmail.getUserId(), productId)) != null) {
            errors.put("Item", "exist in Cart");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {

            CartItem cartItem = new CartItem(userByEmail, product, quantity);
            cartItem.setTotalInCart(cartItem.getQuantity() * cartItem.getProduct().getPrice());
            cartItemsRepository.save(cartItem);
        }
    }

    @Transactional
    @Override
    public void editItemInCart(String userName, long productId, int quantity) throws InvalidInputDataException {
        Map<String, String> errors = new HashMap<>();
        User userByEmail = userRepository.findUserByEmail(userName);
        Product product = productRepository.findAllByProductId(productId);
        if (product.getQuantity() < quantity) {
            errors.put("product quantity", "is not enough");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {

            CartItem cartItem = new CartItem(userByEmail, product, quantity);
            cartItem.setQuantity(quantity);
            cartItem.setTotalInCart(cartItem.getQuantity() * cartItem.getProduct().getPrice());
            cartItemsRepository.save(cartItem);
        }
    }

    @Transactional
    @Override
    public void deleteItemInCart(String userName, long productId) throws InvalidInputDataException {
        Map<String, String> errors = new HashMap<>();
        User userByEmail = userRepository.findUserByEmail(userName);
        Product item = productService.getProductById(productId);
        if (item == null) {
            errors.put("product", "is not found");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            CartItem.CartItemKey key = new CartItem.CartItemKey(userByEmail.getUserId(), productId);
            cartItemsRepository.deleteById(key);
        }
    }

    @Override
    public CartItem findCartItemByKey(CartItem.CartItemKey key) {
        return cartItemsRepository.existsById(key) ? cartItemsRepository.getById(key) : null;
    }

}
