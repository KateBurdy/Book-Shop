package com.example.payments.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Payment already exists")
public class PaymentAlreadyExistsException extends RuntimeException {

    public PaymentAlreadyExistsException(UUID userId, UUID orderId) {
        super(String.format("Payment already exists for user %s and order %s", userId, orderId));
    }
}


