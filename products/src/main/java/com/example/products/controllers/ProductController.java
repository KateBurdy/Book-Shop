package com.example.products.controllers;

import com.example.products.models.dtos.ProductRequest;
import com.example.products.models.dtos.ProductResponse;
import com.example.products.services.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;


    @GetMapping
    public ResponseEntity<List<ProductResponse>> getProducts() {
        List<ProductResponse> products = productService.getProducts();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{product_id}")
    public ResponseEntity<ProductResponse> getProductById(@PathVariable ("product_id") UUID id) {
        ProductResponse product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @GetMapping("/authors/{author_id}")
    public ResponseEntity<List<ProductResponse>> fetchProductsByAuthorId(@PathVariable("author_id") UUID authorId) {
        List<ProductResponse> products = productService.getProductsByAuthorId(authorId);
        return ResponseEntity.ok(products);
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(@Valid @RequestBody ProductRequest productRequest) {
        ProductResponse productResponse = productService.createProduct(productRequest);
        return new ResponseEntity<>(productResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<String> deleteProduct(@PathVariable ("product_id") UUID id) {
        productService.deleteProduct(id);
        return ResponseEntity.ok("Product successfully deleted");
    }
}

