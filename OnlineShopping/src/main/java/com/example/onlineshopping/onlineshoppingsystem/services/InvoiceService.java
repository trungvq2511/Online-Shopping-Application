package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.InvoiceDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface InvoiceService {
    void addInvoice(String userName) throws Exception;

    List<InvoiceDTOResponse> getAllInvoicesByUser(String username);

    List<InvoiceDTOResponse> getAllInvoices(int pageIndex, int pageSize);

    void editInvoice(long invoiceId, String status) throws NotFoundException;
}
