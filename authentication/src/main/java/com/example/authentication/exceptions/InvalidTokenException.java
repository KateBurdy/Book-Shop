package com.example.authentication.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Token is blacklisted")
public class InvalidTokenException extends Throwable {
    public InvalidTokenException(String message) {
        super(message);
    }
}
