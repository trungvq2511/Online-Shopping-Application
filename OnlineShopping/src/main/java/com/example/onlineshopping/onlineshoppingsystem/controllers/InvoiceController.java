package com.example.onlineshopping.onlineshoppingsystem.controllers;

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

    @GetMapping("/get/all-invoices-by-user")
    public ResponseEntity getAllInvoicesByUser() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        return new ResponseEntity(new SuccessResponse(invoiceService.getAllInvoicesByUser(username)), HttpStatus.OK);
    }

    @GetMapping("/get/all-invoices")
    public ResponseEntity getAllInvoices(@RequestParam int pageIndex, @RequestParam int pageSize) {
        return new ResponseEntity(new SuccessResponse(invoiceService.getAllInvoices(pageIndex, pageSize)), HttpStatus.OK);
    }

    @PostMapping("/createInvoice")
    public ResponseEntity createInvoice() throws Exception {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        invoiceService.addInvoice(username);
        return new ResponseEntity(new SuccessResponse("Create invoice successfully"), HttpStatus.OK);
    }

    @PutMapping("/manage/edit-invoice/{invoiceId}")
    public ResponseEntity editInvoice(@PathVariable long invoiceId, @RequestParam String status) throws Exception {
        invoiceService.editInvoice(invoiceId, status);
        return new ResponseEntity(new SuccessResponse("Edit invoice successfully"), HttpStatus.OK);
    }


}
