package com.example.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT , reason = "Invalid order status")
public class InvalidOrderStatusException extends RuntimeException {
    public InvalidOrderStatusException(String status) {
        super(String.format("Status is invalid %s", status));
    }
}
