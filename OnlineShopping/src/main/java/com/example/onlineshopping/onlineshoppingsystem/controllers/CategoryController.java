package com.example.onlineshopping.onlineshoppingsystem.controllers;

import com.example.onlineshopping.onlineshoppingsystem.dto.response.SuccessResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.services.CategoryService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/category")
public class CategoryController {
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping("/get/all-category")
    public ResponseEntity getAllCategories() {
        return new ResponseEntity(new SuccessResponse(categoryService.getAllCatetories()), HttpStatus.OK);
    }

    @PostMapping("/manage/add-category")
    public ResponseEntity addCategory(@RequestParam String name) throws InvalidInputDataException {
        categoryService.addCategory(name);
        return new ResponseEntity(new SuccessResponse("Add category successfully!"), HttpStatus.OK);
    }

    @PutMapping("/manage/edit-category/{categoryId}")
    public ResponseEntity editCategory(@PathVariable Long categoryId, @RequestParam String name) throws InvalidInputDataException, NotFoundException {
        categoryService.editCategory(categoryId, name);
        return new ResponseEntity(new SuccessResponse("Edit category successfully!"), HttpStatus.OK);
    }

    @DeleteMapping("/manage/delete-category/{categoryId}")
    public ResponseEntity deleteCategory(@PathVariable Long categoryId) throws InvalidInputDataException, NotFoundException {
        categoryService.deleteCategory(categoryId);
        return new ResponseEntity(new SuccessResponse("Delete category successfully!"), HttpStatus.OK);
    }
}
