package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.CategoryDTO;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;

import java.util.List;

public interface CategoryService {
    List<CategoryDTO> getAllCatetories();

    void addCategory(String name) throws InvalidInputDataException;

    void editCategory(Long categoryId, String name) throws InvalidInputDataException, NotFoundException;

    void deleteCategory(Long categoryId) throws NotFoundException;
}
