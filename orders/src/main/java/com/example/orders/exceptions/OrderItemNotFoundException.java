package com.example.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order item not found")
public class OrderItemNotFoundException extends RuntimeException {
    public OrderItemNotFoundException(UUID id) {
        super(String.format("Order item with ID: %s not found.", id));
    }
}