package com.example.onlineshopping.onlineshoppingsystem.services.impl;

import com.example.onlineshopping.onlineshoppingsystem.dto.request.ProductDTORequest;
import com.example.onlineshopping.onlineshoppingsystem.dto.response.ProductDTOResponse;
import com.example.onlineshopping.onlineshoppingsystem.entities.file.File;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Category;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Product;
import com.example.onlineshopping.onlineshoppingsystem.entities.product.Rating;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidFileTypeException;
import com.example.onlineshopping.onlineshoppingsystem.exception.InvalidInputDataException;
import com.example.onlineshopping.onlineshoppingsystem.exception.NotFoundException;
import com.example.onlineshopping.onlineshoppingsystem.repositories.CategoryRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.FileRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.ProductRepository;
import com.example.onlineshopping.onlineshoppingsystem.repositories.RatingRepository;
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
    private final RatingRepository ratingRepository;

    public ProductServiceImpl(ProductRepository productRepository,
                              CategoryRepository categoryRepository,
                              ModelMapper modelMapper,
                              FileRepository fileRepository, RatingRepository ratingRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.modelMapper = modelMapper;
        this.fileRepository = fileRepository;
        this.ratingRepository = ratingRepository;
    }

    @Override
    public List<ProductDTOResponse> getAllProduct(int pageIndex, int pageSize) {
        Pageable pageable = PageRequest.of(pageIndex, pageSize);
        Page<Product> products = productRepository.findAll(pageable);
        List<Product> all = products.toList();

        List<ProductDTOResponse> productDTOResponseList = new ArrayList<>();
        for (Product product : all) {
            ProductDTOResponse map = modelMapper.map(product, ProductDTOResponse.class);
            double total = 0;
            List<Rating> allByProduct_productId = ratingRepository.findAllByProduct_ProductId(product.getProductId());
            for (Rating rating : allByProduct_productId) {
                total += rating.getScore();
            }
            map.setRatingScore(total / allByProduct_productId.size());
            productDTOResponseList.add(map);
        }
        return productDTOResponseList;
    }

    @Override
    public Product getProductById(long productId) {
        Optional<Product> byId = productRepository.findById(productId);
        return byId.isPresent() ? byId.get() : null;
    }

    @Override
    public ProductDTOResponse getProductDTOById(long productId) throws NotFoundException {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new NotFoundException("Product is not found"));
        ProductDTOResponse map = modelMapper.map(product, ProductDTOResponse.class);
        List<Rating> allByProduct_productId = ratingRepository.findAllByProduct_ProductId(productId);

        double total = 0;
        for (Rating rating : allByProduct_productId) {
            total += rating.getScore();
        }
        double averageScore = total / allByProduct_productId.size();
        map.setRatingScore(averageScore);
        return map;
    }

    @Transactional
    @Override
    public void addProduct(ProductDTORequest dto) throws InvalidInputDataException, NotFoundException {
        Map<String, String> errors = new HashMap<>();
        if (productRepository.existsByName(dto.getName())) {
            errors.put("Name", "is existed!");
        }
        if (dto.getPrice() < 0) {
            errors.put("Price", "can't be negative number");
        }
        if (dto.getQuantity() < 0)
            errors.put("Quantity", "can't be negative number");
        if (!errors.isEmpty()) {
            throw new InvalidInputDataException(errors);
        } else {
            Product product = modelMapper.map(dto, Product.class);
            //rating score = 0
            product.setRatingScore(0D);

            //categories
            List<Category> categories = new ArrayList<>();
            for (Long aLong : dto.getCategories()) {
                Optional<Category> byId = categoryRepository.findById(aLong);
                if(!byId.isPresent()) {
                    throw new NotFoundException("Category is not found!");
                }
                categories.add(byId.get());
            }
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
            Map<String, String> errors = new HashMap<>();
            if (productRepository.existsByName(dto.getName())) {
                errors.put("Name", "is existed!");
            }
            if (dto.getPrice() < 0) {
                errors.put("Price", "can't be positive number");
            }
            if (dto.getQuantity() < 0)
                errors.put("Quantity", "can't be positive number");
            if (!errors.isEmpty()) {
                throw new InvalidInputDataException(errors);
            } else {
                Product product = productRepository.getById(productId);
                product.setName(dto.getName());
                product.setPrice(dto.getPrice());
                product.setQuantity(dto.getQuantity());
                product.setManufacturedFactory(dto.getManufacturedFactory());
                product.setMadeIn(dto.getMadeIn());

                //categories
                List<Category> categories = new ArrayList<>();
                for (Long aLong : dto.getCategories()) {
                    Optional<Category> byId = categoryRepository.findById(aLong);
                    if(!byId.isPresent()) {
                        throw new NotFoundException("Category is not found!");
                    }
                    categories.add(byId.get());
                }
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
