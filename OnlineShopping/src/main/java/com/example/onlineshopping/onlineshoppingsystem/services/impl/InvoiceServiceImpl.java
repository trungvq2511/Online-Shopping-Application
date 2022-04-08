package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.EnumInvoiceStatus;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.Invoice;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.InvoiceItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.CartItemsRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.InvoiceRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.ProductRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.UserRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.InvoiceService;
import com.example.onlineshopping.onlineshoppingsystem.services.ProductService;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class InvoiceServiceImpl implements InvoiceService {

    private final ModelMapper modelMapper;
    private final CartItemsRepository cartItemsRepository;
    private final UserService userService;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;
    private final InvoiceRepository invoiceRepository;
    private final ProductService productService;


    public InvoiceServiceImpl(ModelMapper modelMapper, CartItemsRepository cartItemsRepository, UserService userService,
                              ProductRepository productRepository, UserRepository userRepository, InvoiceRepository invoiceRepository, ProductService productService) {
        this.modelMapper = modelMapper;
        this.cartItemsRepository = cartItemsRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
        this.productService = productService;
    }

    @Override
    public void addInvoice(String userName) throws Exception {
        Map<String, String> errors = new HashMap<>();
        User userByEmail = userRepository.findUserByEmail(userName);
        if (userByEmail == null) {
            errors.put("user", "is not found");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            List<CartItem> cartItems = cartItemsRepository.findAllByUser_UserId(userByEmail.getUserId());
            if(cartItems == null) {
                throw new NotFoundException("Create invoice fail because user "+userByEmail.getUserId()+"don't have items in cart");
            } else {
                Invoice newInvoice = new Invoice();
                List<InvoiceItem> invoiceItems = cartItems.stream().map(cartItem ->
                                new InvoiceItem(newInvoice, cartItem.getProduct(), cartItem.getQuantity(), cartItem.getProduct().getPrice()))
                        .collect(Collectors.toList());

                cartItems.forEach(cartItem -> {
                    Product productById = productService.getProductById(cartItem.getProduct().getProductId());
                    int availableQuantityAfterCreateInvoice = productById.getQuantity() - cartItem.getQuantity();
                    productById.setQuantity(availableQuantityAfterCreateInvoice);
                    productRepository.save(productById);
                });
                newInvoice.setUser(userByEmail);
                newInvoice.setItems(invoiceItems);
                newInvoice.setStatus(EnumInvoiceStatus.ACCEPTED_ORDER);
                //Total Invoice
                double sum = 0L;
                for (InvoiceItem items : invoiceItems) {
                    double l = items.getQuantity() * items.getPrice();
                    sum += l;
                }
                newInvoice.setTotal(sum);
                invoiceRepository.save(newInvoice);
                cartItemsRepository.removeAllByUserUserId(userByEmail.getUserId());
            }
        }
    }

}
