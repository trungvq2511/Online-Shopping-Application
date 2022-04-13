package com.example.onlineshopping.onlineshoppingsystem.services;

import org.springframework.stereotype.Service;

@Service
public interface InvoiceService {
    void addInvoice(String userName) throws Exception;

}
