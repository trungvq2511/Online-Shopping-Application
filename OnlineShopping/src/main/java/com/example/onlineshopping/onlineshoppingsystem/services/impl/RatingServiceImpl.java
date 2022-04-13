package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.RatingDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.RatingDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Rating;
import com.example.onlineshopping.onlineshoppingsystem.entities.user.User;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.RatingRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.UserRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.ProductService;
import com.example.onlineshopping.onlineshoppingsystem.services.RatingService;
import com.example.onlineshopping.onlineshoppingsystem.services.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
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
    private final UserRepository userRepository;

    public RatingServiceImpl(ModelMapper modelMapper, RatingRepository ratingRepository, UserService userService, ProductService productService, UserRepository userRepository) {
        this.modelMapper = modelMapper;
        this.ratingRepository = ratingRepository;
        this.userService = userService;
        this.productService = productService;
        this.userRepository = userRepository;
    }


    @Override
    public List<RatingDTOResponse> getRatingOfProduct(Long productId, int PageIndex, int PageSize) {
        Pageable pageable = PageRequest.of(PageIndex, PageSize);
        return ratingRepository.findAllByProduct_ProductId(productId, pageable).stream().map(rating -> {
            RatingDTOResponse dto = modelMapper.map(rating, RatingDTOResponse.class);
            dto.setProductId(rating.getProduct().getProductId());
            dto.setUserId(rating.getUser().getUserId());
            return dto;
        }).collect(Collectors.toList());
    }

    @Transactional
    @Override
    public void createRating(String userName, RatingDTORequest dto) throws InvalidInputDataException {
        saveRating(userName, dto.getProductId(), dto.getComment(), dto.getScore());
    }

    @Transactional
    @Override
    public void editRating(String userName, RatingDTORequest dto) throws InvalidInputDataException {
        saveRating(userName, dto.getProductId(), dto.getComment(), dto.getScore());
    }

    private void saveRating(String userName, Long productId, String comment, Double score) throws InvalidInputDataException {
        Map<String, String> errors = new HashMap<>();
        User userByEmail = userRepository.findUserByEmail(userName);
        if (userByEmail == null) {
            errors.put("user", "is not found");
        }
        Product item = productService.getProductById(productId);
        if (item == null) {
            errors.put("product", "is not found");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            ratingRepository.save(new Rating(userByEmail, item, comment, score));
        }
    }

    @Transactional
    @Override
    public void deleteRating(String userName, Long productId) throws InvalidInputDataException {

        Map<String, String> errors = new HashMap<>();
        User userByEmail = userRepository.findUserByEmail(userName);
        if (userByEmail == null) {
            errors.put("user", "is not found");
        }
        Product item = productService.getProductById(productId);
        if (item == null) {
            errors.put("product", "is not found");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            Rating.UserProductKey key = new Rating.UserProductKey(userByEmail.getUserId(), productId);
            ratingRepository.deleteById(key);
        }
    }
}

