package com.example.payments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "Payment not found")
public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(UUID userId, UUID orderId) {
        super(String.format("Payment not found for user %s and order %s", userId, orderId));
    }
}

