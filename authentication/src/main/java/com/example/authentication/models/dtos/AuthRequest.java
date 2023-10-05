package com.example.authentication.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

import jakarta.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class AuthRequest {

    @Pattern(regexp = "^[a-zA-Z0-9]{3,50}$", message = "Username should contain only alphanumeric characters and be between 3 and 50 characters")
    private String username;

    @Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z]).{8,}$", message = "Password should have at least one number, one lowercase and one uppercase character, and be at least 8 characters long")
    private String password;
}
