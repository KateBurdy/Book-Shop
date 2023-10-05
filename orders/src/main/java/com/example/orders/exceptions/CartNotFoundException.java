package com.example.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Cart not found")
public class CartNotFoundException extends RuntimeException {
    public CartNotFoundException(UUID id) {
        super(String.format("Cart with ID: %s not found.", id));
    }
}