package com.example.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Author not found")
public class AuthorNotFoundException extends RuntimeException{
    public AuthorNotFoundException(UUID id) {
        super(String.format("Author not found with id %s", id));
    }
}
