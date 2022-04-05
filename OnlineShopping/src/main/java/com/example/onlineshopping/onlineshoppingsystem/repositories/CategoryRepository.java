package com.example.onlineshopping.onlineshoppingsystem.repositories;

import com.example.onlineshopping.onlineshoppingsystem.entities.product.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {
}
