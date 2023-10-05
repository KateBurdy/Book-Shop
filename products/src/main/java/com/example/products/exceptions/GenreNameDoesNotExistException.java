package com.example.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value= HttpStatus.NOT_FOUND, reason = "Genre with this id does not exist")
public class GenreNameDoesNotExistException extends RuntimeException {
    public GenreNameDoesNotExistException(UUID id) {
        super(String.format("Genre with this id does not exist: %s",id));
    }
}
