package com.example.authentication.controllers;


import com.example.authentication.models.dtos.AuthRequest;
import com.example.authentication.models.User;
import com.example.authentication.services.AuthService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    @PostMapping("register")
    public ResponseEntity<String> register(@Valid @RequestBody AuthRequest request) {
        User user = authService.register(request);
        return ResponseEntity.ok("User " + user.getUsername() + " successfully registered");
    }

    @PostMapping("login")
    public ResponseEntity<String> login(@Valid @RequestBody AuthRequest request) {
        String token = authService.login(request);
        return ResponseEntity.ok()
                .header(
                        HttpHeaders.AUTHORIZATION,
                        token
                )
                .body("User logged in successfully");
    }
}
