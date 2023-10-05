package com.example.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Product not found")
public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID productId) {
        super(String.format("Product not found with id %s", productId));
    }
}

