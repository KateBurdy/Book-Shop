package com.example.authors.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Author with this name already exists")
public class AuthorNameAlreadyExistsException extends RuntimeException {
    public AuthorNameAlreadyExistsException(String authorName) {
        super(String.format("Author with this name already exists", authorName));
    }
}
