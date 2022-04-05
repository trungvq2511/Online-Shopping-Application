package com.example.onlineshopping.onlineshoppingsystem.services;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.ProductDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.ProductDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface ProductService {
    List<ProductDTOResponse> getAllProduct(int pageIndex, int pageSize);

    ProductDTOResponse getProductById(long productId) throws NotFoundException;

    void deleteById(long productId) throws NotFoundException;

    void addProduct(ProductDTORequest dto) throws InvalidInputDataException;

    void editProductBasicInfo(Long productId, ProductDTORequest dto) throws Exception;

    void editProductFiles(Long productId, List<MultipartFile> uploadFiles) throws NotFoundException, IOException, Exception;
}
