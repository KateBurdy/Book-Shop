package com.example.orders.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.INTERNAL_SERVER_ERROR , reason = "Could not create order")
public class OrderCreationException extends RuntimeException {
    public OrderCreationException() {
        super("Could not create order");
    }
}
