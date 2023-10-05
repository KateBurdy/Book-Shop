package com.example.users.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.UUID;

@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "User not found")
public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(UUID id) {
        super(String.format("User with id %s not found", id));
    }

    public UserNotFoundException () {
        super("User not found");
    }
}
