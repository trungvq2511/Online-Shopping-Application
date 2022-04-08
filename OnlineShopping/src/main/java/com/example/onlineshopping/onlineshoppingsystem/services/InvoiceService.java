package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.InvoiceRequestDTO;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.Invoice;
import org.springframework.stereotype.Service;

@Service
public interface InvoiceService {
    void addInvoice(String userName) throws Exception;

}
