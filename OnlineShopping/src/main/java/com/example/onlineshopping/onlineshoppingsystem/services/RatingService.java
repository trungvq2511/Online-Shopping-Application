package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.RatingDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.RatingDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    List<RatingDTOResponse> getRatingOfProduct(Long ProductId, int PageIndex, int PageSize) ;
    void createRating(Long userId, RatingDTORequest dto) throws InvalidInputDataException;
    void editRating(Long userId, RatingDTORequest dto) throws InvalidInputDataException;
    void deleteRating(Long userId, Long productId) throws InvalidInputDataException;
}
