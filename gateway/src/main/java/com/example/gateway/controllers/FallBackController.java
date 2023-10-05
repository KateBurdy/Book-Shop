package com.example.gateway.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/fallback")
public class FallBackController {

    @GetMapping("/fallback")
    public ResponseEntity<String> requestFallback() {
        return new ResponseEntity<>("Gateway fallback",
                HttpStatus.INTERNAL_SERVER_ERROR);
    }
}