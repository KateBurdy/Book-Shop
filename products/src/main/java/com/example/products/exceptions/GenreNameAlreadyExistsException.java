package com.example.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value= HttpStatus.CONFLICT, reason = "Genre already exists with name")
public class GenreNameAlreadyExistsException extends RuntimeException {
    public GenreNameAlreadyExistsException(String name) {
        super(String.format("Genre already exists with name %s",name));
    }
}
