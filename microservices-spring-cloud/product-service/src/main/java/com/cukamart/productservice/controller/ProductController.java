package com.cukamart.productservice.controller;

import com.cukamart.productservice.dto.ProductRequest;
import com.cukamart.productservice.dto.ProductResponse;
import com.cukamart.productservice.mapper.ProductMapper;
import com.cukamart.productservice.model.Product; // todo no domain in controller
import com.cukamart.productservice.service.ProductService;
import com.mongodb.MongoWriteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/product")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProductResponse> getAllExpenses() {
        return ProductMapper.MAPPER.convert(productService.getAllProducts());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProductResponse createProduct(@RequestBody ProductRequest productRequest) {
        return ProductMapper.MAPPER.convert(productService.createProduct(productRequest));
    }

    @PutMapping
    public Product updateExpense(Product expense) {
        return productService.updateProduct(expense);
    }

    @GetMapping("/{name}")
    public void getProductByName(@PathVariable String name) {

    }

    @DeleteMapping
    public void deleteExpense(String productName) {
        productService.deleteProduct(productName);
    }

    @ExceptionHandler(MongoWriteException.class)
    public ResponseEntity<String> handleNoSuchElementFoundException(MongoWriteException exception) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(exception.getMessage());
    }
}
