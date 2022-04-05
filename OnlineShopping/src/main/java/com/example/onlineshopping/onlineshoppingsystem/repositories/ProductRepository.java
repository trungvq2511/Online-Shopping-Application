package com.example.onlineshopping.onlineshoppingsystem.repositories;

import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
