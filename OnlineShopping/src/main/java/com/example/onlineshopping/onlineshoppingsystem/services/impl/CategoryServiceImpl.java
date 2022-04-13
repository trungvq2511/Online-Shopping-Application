package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.CategoryDTO;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Category;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.CategoryRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.CategoryService;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class CategoryServiceImpl implements CategoryService {
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;

    public CategoryServiceImpl(CategoryRepository categoryRepository,
                               ModelMapper modelMapper) {
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<CategoryDTO> getAllCatetories() {
        List<Category> all = categoryRepository.findAll();
        List<CategoryDTO> categoryDTOList = all.stream().map(category -> modelMapper.map(category, CategoryDTO.class)).collect(Collectors.toList());
        return categoryDTOList;
    }

    @Override
    public void addCategory(String name) throws InvalidInputDataException {
        Map<String, String> errors = new HashMap<>();
        if (name == null || name.length() == 0) {
            errors.put("Category name", "can't be blank!");
        }
        if (!name.matches("[a-zA-Z]+")) {
            errors.put("Category name", "must contain letter only!");
        }
        if(categoryRepository.existsByName(name)) {
            errors.put("Category name", "is existed!");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            categoryRepository.save(new Category(null, name, null));
        }
    }

    @Override
    public void editCategory(Long categoryId, String name) throws InvalidInputDataException, NotFoundException {
        if(!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category is not found!");
        }
        Map<String, String> errors = new HashMap<>();
        if (name == null || name.length() == 0) {
            errors.put("Category name", "can't be blank!");
        }
        if (!name.matches("[a-zA-Z]+")) {
            errors.put("Category name", "must contain letter only!");
        }
        if(categoryRepository.existsByName(name)) {
            errors.put("Category name", "is existed!");
        }
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            Category byId = categoryRepository.getById(categoryId);
            byId.setName(name);
            categoryRepository.save(byId);
        }
    }

    @Override
    public void deleteCategory(Long categoryId) throws NotFoundException {
        if(!categoryRepository.existsById(categoryId)) {
            throw new NotFoundException("Category is not found!");
        }
        categoryRepository.deleteById(categoryId);
    }
}
