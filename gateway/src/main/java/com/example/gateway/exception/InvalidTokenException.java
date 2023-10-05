package com.example.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = "Invalid token exception")
public class InvalidTokenException extends RuntimeException {
    public InvalidTokenException(String message) {
        super(String.format(message));
    }
}
