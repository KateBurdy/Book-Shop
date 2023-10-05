package com.example.payments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Failed to update order status")
public class OrderStatusUpdateException extends RuntimeException {

    public OrderStatusUpdateException(UUID orderId, String errorMessage) {
        super(String.format("Failed to update order status for orderId %s: %s", orderId, errorMessage));
    }
}