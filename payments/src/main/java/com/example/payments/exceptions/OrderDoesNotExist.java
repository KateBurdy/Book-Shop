package com.example.payments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Order does not exist")
public class OrderDoesNotExist extends RuntimeException {
    public OrderDoesNotExist(UUID orderId) {
        super(String.format("Order with id %s doesn not exist", orderId));
    }
}