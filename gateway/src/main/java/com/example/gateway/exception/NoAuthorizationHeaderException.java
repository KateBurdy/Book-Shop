package com.example.gateway.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="No authorization header found")
public class NoAuthorizationHeaderException extends RuntimeException{
    public NoAuthorizationHeaderException (){
        super("No authorization header found");
    }
}
