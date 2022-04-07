package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.InvoiceRequestDTO;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.services.InvoiceService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/invoice")
public class InvoiceController {
    private final InvoiceService invoiceService;

    public InvoiceController(InvoiceService invoiceService) {
        this.invoiceService = invoiceService;
    }

    @PostMapping("/createInvoice")
    public ResponseEntity createInvoice(@RequestParam Long userId, @RequestBody InvoiceRequestDTO dto) throws Exception{
        invoiceService.addInvoice(userId,dto);
        return new ResponseEntity(new SuccessResponse("Create invoice success"), HttpStatus.OK);
    }
}
