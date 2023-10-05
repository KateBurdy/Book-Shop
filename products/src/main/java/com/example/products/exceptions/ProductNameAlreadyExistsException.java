package com.example.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Product with this name already exists")
public class ProductNameAlreadyExistsException extends RuntimeException {
    public ProductNameAlreadyExistsException(String productName) {
        super(String.format("Product with name: %s already exists.", productName));
    }
}
