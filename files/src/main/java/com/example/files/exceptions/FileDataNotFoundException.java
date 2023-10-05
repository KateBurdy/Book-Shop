package com.example.files.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class FileDataNotFoundException extends RuntimeException {
    public FileDataNotFoundException(UUID fileId) {
        super(String.format("File data not found for file ID: %s", fileId));
    }
}


