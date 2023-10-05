package com.example.authentication.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = "Invalid credentials")
public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException(){
        super("Invalid credentials");
    }
}




