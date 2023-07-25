package com.cukamart.productservice.service;

import com.cukamart.productservice.dto.ProductRequest;
import com.cukamart.productservice.mapper.ProductMapper;
import com.cukamart.productservice.model.Product;
import com.cukamart.productservice.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private static final Logger log = LoggerFactory.getLogger(ProductService.class);

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public Product createProduct(ProductRequest productRequest) {
        Product product = ProductMapper.MAPPER.convert(productRequest);
        log.info("Product is about to get created {}", product.getProductName());
        return productRepository.save(product);
    }

    public Product updateProduct(Product product) {
        Product savedProduct = productRepository.findById(product.getId())
                .orElseThrow(() -> new RuntimeException(String.format("Cannot find product by ID %s", product.getId())));

        savedProduct.setProductName(product.getProductName());
        savedProduct.setDescription(product.getDescription());
        savedProduct.setPrice(product.getPrice());

        return productRepository.save(product);
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public void deleteProduct(String name) {
        productRepository.deleteByProductName(name);
    }
}
