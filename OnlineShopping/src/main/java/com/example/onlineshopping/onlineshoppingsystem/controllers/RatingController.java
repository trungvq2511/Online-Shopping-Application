package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.RatingDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/rating")
public class RatingController {
    private final RatingService ratingService;

    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @GetMapping("/getRatingOfProduct")
    public ResponseEntity getRatingOfProduct(@RequestParam long productId,
                                             @RequestParam int pageIndex, @RequestParam int pageSize) {
        ratingService.getRatingOfProduct(productId, pageIndex, pageSize);
        return new ResponseEntity(new SuccessResponse("Show Rating to one Product successfully!"), HttpStatus.OK);
    }

    @PostMapping("/createRating/{userId}")
    public ResponseEntity createRating(@PathVariable Long userId, @RequestBody RatingDTORequest dto)
            throws InvalidInputDataException, NotFoundException {
        ratingService.createRating(userId, dto);
        return new ResponseEntity(new SuccessResponse("Create rating successfully!"), HttpStatus.OK);
    }

    @PutMapping("/editRating/{userId}")
    public ResponseEntity editRating(@PathVariable Long userId, @RequestBody RatingDTORequest dto)
            throws InvalidInputDataException {
        ratingService.editRating(userId, dto);
        return new ResponseEntity(new SuccessResponse("Edit Rating of User successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRating/{userId}&{productId}")
    public ResponseEntity deleteRating(@PathVariable long userId, @PathVariable long productId)
            throws InvalidInputDataException {
        ratingService.deleteRating(userId, productId);
        return new ResponseEntity(new SuccessResponse("Delete Rating successfully!"), HttpStatus.OK);
    }
}

