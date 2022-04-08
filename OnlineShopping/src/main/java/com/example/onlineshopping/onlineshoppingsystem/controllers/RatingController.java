package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.RatingDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.services.RatingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
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
        ratingService.getRatingOfProduct(productId,pageIndex,pageSize);
        return new ResponseEntity(new SuccessResponse("Show Rating to one Product successfully!"), HttpStatus.OK);
    }

    @PostMapping("/createRating")
    public ResponseEntity createRating( @RequestBody RatingDTORequest dto)
            throws InvalidInputDataException, NotFoundException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ratingService.createRating(username,dto);
        return new ResponseEntity(new SuccessResponse("Create rating successfully!"), HttpStatus.OK);
    }

    @PutMapping("/editRating")
    public ResponseEntity editRating(@RequestBody RatingDTORequest dto)
            throws InvalidInputDataException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ratingService.editRating(username,dto);
        return new ResponseEntity(new SuccessResponse("Edit Rating of User successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/deleteRating/{productId}")
    public ResponseEntity deleteRating(@PathVariable long productId)
            throws InvalidInputDataException {
        String username = SecurityContextHolder.getContext().getAuthentication().getPrincipal().toString();
        ratingService.deleteRating(username,productId);
        return new ResponseEntity(new SuccessResponse("Delete Rating successfully!"), HttpStatus.OK);
    }
}

