package com.example.onlineshopping.onlineshoppingsystem.repositories;

import com.example.onlineshopping.onlineshoppingsystem.entities.invoice.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, UUID> {
}
