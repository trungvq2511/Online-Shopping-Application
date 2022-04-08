package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.RatingDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.RatingDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Rating;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.RatingRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.ProductService;
import com.example.onlineshopping.onlineshoppingsystem.services.RatingService;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RatingServiceImpl implements RatingService {

    private final ModelMapper modelMapper;
    private final RatingRepository ratingRepository;
    private final UserService userService;
    private final ProductService productService;

    public RatingServiceImpl(ModelMapper modelMapper, RatingRepository ratingRepository, UserService userService, ProductService productService) {
        this.modelMapper = modelMapper;
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.productService = productService;
    }


    @Override
    public List<RatingDTOResponse> getRatingOfProduct(Long productId, int PageIndex, int PageSize)  {
        Pageable pageable = PageRequest.of(PageIndex,PageSize);
        return ratingRepository.findAllByProduct_ProductId(productId,pageable).stream().map(rating -> {
            RatingDTOResponse dto = modelMapper.map(rating,RatingDTOResponse.class);
            dto.setProductId(rating.getProduct().getProductId());
            dto.setUserId(rating.getUser().getUserId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Override
    public void createRating(Long userId, RatingDTORequest dto) throws InvalidInputDataException {
        saveRating(userId,dto.getProductId(),dto.getComment(), dto.getScore());
    }

    @Override
    public void editRating(Long userId, RatingDTORequest dto) throws InvalidInputDataException {
        saveRating(userId,dto.getProductId(),dto.getComment(), dto.getScore());
    }

    private void saveRating(Long userId, Long productId, String comment, Double score) throws InvalidInputDataException {
        Map<String, String> errors = new HashMap<>();
        User userById = userService.getUserById(userId);
        if (userById != null) {
            errors.put("user", "is not found");
        }
        Product item = productService.getProductById(productId);
        if (item != null) {
            errors.put("product", "is not found");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            ratingRepository.save(new Rating(userById,item,comment,score));
        }
    }

    @Override
    public void deleteRating(Long userId, Long productId) throws InvalidInputDataException {

        Map<String, String> errors = new HashMap<>();
        User userById = userService.getUserById(userId);
        if (userById != null) {
            errors.put("user", "is not found");
        }
        Product item = productService.getProductById(productId);
        if (item != null) {
            errors.put("product", "is not found");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            Rating.UserProductKey key = new Rating.UserProductKey(userId, productId);
            ratingRepository.deleteById(key);
        }
    }
}

