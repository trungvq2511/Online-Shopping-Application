package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.InvoiceDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.cart.CartItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.EnumInvoiceStatus;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.Invoice;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.InvoiceItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.*;
import com.example.onlineshopping.onlineshoppingsystem.services.InvoiceService;
import com.example.onlineshopping.onlineshoppingsystem.services.ProductService;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
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
    private final InvoiceItemRepository invoiceItemRepository;
    private final ProductService productService;


    public InvoiceServiceImpl(ModelMapper modelMapper,
                              CartItemsRepository cartItemsRepository,
                              UserService userService,
                              ProductRepository productRepository,
                              UserRepository userRepository,
                              InvoiceRepository invoiceRepository,
                              InvoiceItemRepository invoiceItemRepository,
                              ProductService productService) {
        this.modelMapper = modelMapper;
        this.cartItemsRepository = cartItemsRepository;
        this.userService = userService;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
        this.invoiceRepository = invoiceRepository;
        this.invoiceItemRepository = invoiceItemRepository;
        this.productService = productService;
    }

    @Transactional
    @Override
    public void addInvoice(String userName) throws Exception {
        Map<String, String> errors = new HashMap<>();
        User userByEmail = userRepository.findUserByEmail(userName);
        if (userByEmail.getVerified() == false) {
            errors.put("user", "is not verified yet!");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            List<CartItem> cartItems = cartItemsRepository.findAllByUser_UserId(userByEmail.getUserId());
            if (cartItems.size() == 0) {
                throw new NotFoundException("Create invoice fail because user id = " + userByEmail.getUserId() + " don't have items in cart");
            } else {
                //add invoice
                Invoice newInvoice = new Invoice();
                newInvoice.setUser(userByEmail);
                newInvoice.setStatus(EnumInvoiceStatus.ACCEPTED_ORDER);
                //total invoice
                double sum = 0L;
                for (CartItem item : cartItems) {
                    double l = item.getTotalInCart();
                    sum += l;
                }
                newInvoice.setTotal(sum);
                Invoice save = invoiceRepository.save(newInvoice);

                //add invoice item
                List<InvoiceItem> invoiceItems = new ArrayList<>();
                for (CartItem item : cartItems) {
                    InvoiceItem invoiceItem = new InvoiceItem(save.getInvoiceId(),
                            item.getProduct().getProductId(),
                            item.getQuantity(),
                            item.getProduct().getPrice(),
                            item.getTotalInCart());
                    invoiceItems.add(invoiceItem);
                }
                invoiceItemRepository.saveAll(invoiceItems);

                //update product quantity
                List<Product> productLists = new ArrayList<>();
                for (CartItem cartItem : cartItems) {
                    Product productById = productService.getProductById(cartItem.getProduct().getProductId());
                    int availableQuantityAfterCreateInvoice = productById.getQuantity() - cartItem.getQuantity();
                    productById.setQuantity(availableQuantityAfterCreateInvoice);

                    productLists.add(productById);
                }
                productRepository.saveAll(productLists);

                //clear cart
                cartItemsRepository.removeAllByUserUserId(userByEmail.getUserId());
            }
        }
    }

    @Override
    public List<InvoiceDTOResponse> getAllInvoicesByUser(String username) {
        User userByEmail = userRepository.findUserByEmail(username);
        List<Invoice> allByUserUserId = invoiceRepository.getAllByUserUserId(userByEmail.getUserId());
        List<InvoiceDTOResponse> invoiceDTOResponseList = allByUserUserId.stream().map(invoice -> modelMapper.map(invoice, InvoiceDTOResponse.class)).collect(Collectors.toList());
        return invoiceDTOResponseList;
    }

    @Override
    public List<InvoiceDTOResponse> getAllInvoices(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Invoice> invoices = invoiceRepository.findAll(pageable);
        List<InvoiceDTOResponse> invoiceDTOResponseList = invoices.stream().map(invoice -> modelMapper.map(invoice, InvoiceDTOResponse.class)).collect(Collectors.toList());
        return invoiceDTOResponseList;
    }

    @Override
    public void editInvoice(long invoiceId, String status) throws NotFoundException {
        if (!invoiceRepository.existsByInvoiceId(invoiceId)) {
            throw new NotFoundException("Invoice is not found!");
        }
        Invoice byId = invoiceRepository.getById(invoiceId);

        try {
            EnumInvoiceStatus invoiceStatus = EnumInvoiceStatus.valueOf(status);
            byId.setStatus(invoiceStatus);
        } catch (Exception exception) {
            throw new NotFoundException("Invoice status must be: ACCEPTED_ORDER or DELIVERED or CANCELED_ORDER or ERROR!");
        }
        invoiceRepository.save(byId);
    }

}
