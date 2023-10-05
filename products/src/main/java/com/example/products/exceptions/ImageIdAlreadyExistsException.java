package com.example.products.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.CONFLICT, reason = "Image ID already exists")
public class ImageIdAlreadyExistsException extends RuntimeException {
    public ImageIdAlreadyExistsException(UUID imageId) {
        super(String.format("Image with ID: %s already exists.", imageId));
    }
}
