package com.example.authors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;
import java.util.UUID;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Author not found")
public class AuthorNotFoundException extends RuntimeException {
    public AuthorNotFoundException(UUID uuid) {
        super(String.format("Author not found with id %s", uuid));
    }

}

