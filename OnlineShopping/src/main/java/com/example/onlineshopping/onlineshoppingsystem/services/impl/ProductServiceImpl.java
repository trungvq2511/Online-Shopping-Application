package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.ProductDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.ProductDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.file.File;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Category;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidFileTypeException;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.CategoryRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.FileRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.ProductRepository;
import com.example.onlineshopping.onlineshoppingsystem.services.ProductService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.transaction.Transactional;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ProductServiceImpl implements ProductService {
    private static final Path PROJECT_PATH = Paths.get(System.getProperty("user.dir"));
    private static final Path STATIC_PATH = Paths.get("static");
    private static final Path FILE_PATH = Paths.get("product-files");

    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileRepository fileRepository;
    private final ModelMapper modelMapper;


    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ModelMapper modelMapper,
                              FileRepository fileRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.fileRepository = fileRepository;
    }

    @Override
    public List<ProductDTOResponse> getAllProduct(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        List<Product> all = products.toList();

        List<ProductDTOResponse> productDTOResponseList = all.stream().map(product -> modelMapper.map(product, ProductDTOResponse.class)).collect(Collectors.toList());
        return productDTOResponseList;
    }

    @Override
    public Product getProductById(long productId) {
        Optional<Product> byId = productRepository.findById(productId);
        return byId.isPresent() ? null : byId.get();
    }

    @Override
    public ProductDTOResponse getProductDTOById(long productId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product is not found"));
        return modelMapper.map(product, ProductDTOResponse.class);
    }

    @Transactional
    @Override
    public void addProduct(ProductDTORequest dto) throws InvalidInputDataException {
        Map<String, String> exceptionList = new HashMap<>();
        if (dto.getPrice() < 0) {
            exceptionList.put("Price", "can't be positive number");
        }
        if (dto.getQuantity() < 0)
            exceptionList.put("Quantity", "can't be positive number");
        if (!exceptionList.isEmpty()) {
            throw new InvalidInputDataException(exceptionList);
        } else {
            Product product = modelMapper.map(dto, Product.class);
            //rating score = 0
            product.setRatingScore(0D);

            //categories
            List<Category> categories = dto.getCategories().stream().map(categoryRepository::getById).collect(Collectors.toList());
            product.setCategories(categories);

            productRepository.save(product);
        }
    }

    @Transactional
    @Override
    public void editProductBasicInfo(Long productId, ProductDTORequest dto) throws Exception {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product with id = " + productId + " is not found!");
        } else {
            Map<String, String> exceptionList = new HashMap<>();
            if (dto.getPrice() < 0) {
                exceptionList.put("Price", "can't be positive number");
            }
            if (dto.getQuantity() < 0)
                exceptionList.put("Quantity", "can't be positive number");
            if (!exceptionList.isEmpty()) {
                throw new InvalidInputDataException(exceptionList);
            } else {
                Product product = productRepository.getById(productId);
                product.setName(dto.getName());
                product.setPrice(dto.getPrice());
                product.setQuantity(dto.getQuantity());
                product.setManufacturedFactory(dto.getManufacturedFactory());
                product.setMadeIn(dto.getMadeIn());

                //categories
                List<Category> categories = dto.getCategories().stream().map(categoryRepository::getById).collect(Collectors.toList());
                product.setCategories(categories);

                productRepository.save(product);
            }
        }
    }

    @Transactional
    @Override
    public void editProductFiles(Long productId, List<MultipartFile> uploadFiles) throws Exception {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product with id = " + productId + " is not found!");
        } else {
            Product product = productRepository.getById(productId);

            //delete old files
            deleteOldFiles(product);

            List<File> saveFiles = new ArrayList<>();

            //send file to server
            for (MultipartFile f : uploadFiles) {
                try {
                    //check image or video only
                    if (!(f.getContentType().contains("image") || f.getContentType().contains("video"))) {
                        throw new InvalidFileTypeException("You can just upload images or videos!");
                    } else {
                        String now = String.valueOf(System.nanoTime());

                        //format filename
                        String filename = now + "_" + f.getOriginalFilename();

                        //save to disk
                        if (!Files.exists(PROJECT_PATH.resolve(STATIC_PATH).resolve(FILE_PATH))) {
                            Files.createDirectories(PROJECT_PATH.resolve(STATIC_PATH).resolve(FILE_PATH));
                        }
                        Path savePath = PROJECT_PATH
                                .resolve(STATIC_PATH)
                                .resolve(FILE_PATH)
                                .resolve(filename);

                        try (OutputStream os = Files.newOutputStream(savePath)) {
                            os.write(f.getBytes());
                            System.out.println(savePath);
                        }

                        File file = new File();
                        if (f.getOriginalFilename() != null) {
                            file.setName(StringUtils.cleanPath(f.getOriginalFilename()));
                        } else {
                            file.setName(f.getOriginalFilename());
                        }
                        file.setType(f.getContentType());
                        file.setUploadTime(LocalDateTime.now());
                        file.setUrl(FILE_PATH.resolve(filename).toString());
                        file.setProduct(product);

                        saveFiles.add(file);
                    }
                } catch (NullPointerException exception) {
                    System.err.println("NO FILE");
                    //delete old files
                    deleteOldFiles(product);
                }
            }
            fileRepository.saveAll(saveFiles);
        }
    }

    public void deleteOldFiles(Product product) {
        if (product.getFiles().size() > 0) {
            product.getFiles().forEach(file -> {
                try {
                    Path path = PROJECT_PATH.resolve(STATIC_PATH).resolve(file.getUrl());
                    Files.delete(path);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            fileRepository.deleteAll(product.getFiles());
        }
    }

    @Transactional
    @Override
    public void deleteById(long productId) throws NotFoundException {
        if (!productRepository.existsById(productId)) {
            throw new NotFoundException("Product with id = " + productId + " is not found!");
        } else {
            productRepository.deleteById(productId);
        }
    }
}
