package com.example.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;
@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Genre with this id doesn't exist")
public class GenreNotFoundException extends RuntimeException {
    public GenreNotFoundException(UUID id) {
        super(String.format("Genre not found with id: %s", id));
    }
}
