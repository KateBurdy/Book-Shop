package com.example.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order not found")
public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(UUID id) {
        super(String.format("Order with ID: %s not found.", id));
    }
}