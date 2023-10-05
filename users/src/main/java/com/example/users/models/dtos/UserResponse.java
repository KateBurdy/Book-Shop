package com.example.users.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@NoArgsConstructor
public class UserResponse {
    private UUID id;
    private String username;
}
