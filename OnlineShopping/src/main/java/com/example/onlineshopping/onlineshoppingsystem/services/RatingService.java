package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.RatingDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.RatingDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface RatingService {
    List<RatingDTOResponse> getRatingOfProduct(Long ProductId, int PageIndex, int PageSize);

    void createRating(String userName, RatingDTORequest dto) throws InvalidInputDataException;

    void editRating(String userName, RatingDTORequest dto) throws InvalidInputDataException;

    void deleteRating(String userName, Long productId) throws InvalidInputDataException;
}
