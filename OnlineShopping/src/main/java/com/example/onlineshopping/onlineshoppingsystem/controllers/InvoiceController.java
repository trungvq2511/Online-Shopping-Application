package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.InvoiceRequestDTO;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @GetMapping("/createInvoice")
    public ResponseEntity createInvoice() throws Exception{
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        invoiceService.addInvoice(username);
        return new ResponseEntity(new SuccessResponse("Create invoice success"), HttpStatus.OK);
    }
}
