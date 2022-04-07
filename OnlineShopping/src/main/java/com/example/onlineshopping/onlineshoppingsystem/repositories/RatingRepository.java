package com.example.onlineshopping.onlineshoppingsystem.repositories;

import com.example.onlineshopping.onlineshoppingsystem.entities.product.Rating;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Rating.UserProductKey;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RatingRepository extends JpaRepository<Rating, UserProductKey> {
    List<Rating> findAllByProduct_ProductId (Long productId, Pageable pageable);
}