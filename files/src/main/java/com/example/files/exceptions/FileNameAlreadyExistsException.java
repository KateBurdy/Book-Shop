package com.example.files.exceptions;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.CONFLICT, reason ="File with this name already exists")
public class FileNameAlreadyExistsException extends RuntimeException {

    public FileNameAlreadyExistsException(String filename) {
        super(String.format("File with name: %s already exists.", filename));
    }
}
