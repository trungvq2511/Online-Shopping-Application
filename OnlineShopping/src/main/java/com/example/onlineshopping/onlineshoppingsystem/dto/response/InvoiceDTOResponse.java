package com.example.onlineshopping.onlineshoppingsystem.dto.response;

import com.example.onlineshopping.onlineshoppingsystem.dto.InvoiceItemDTO;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.EnumInvoiceStatus;
import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.InvoiceItem;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class InvoiceDTOResponse {
    private Long invoiceId;
    private Double total;
    private String status;
    List<InvoiceItemDTO> items;
}
