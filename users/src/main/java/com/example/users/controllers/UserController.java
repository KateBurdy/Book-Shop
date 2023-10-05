package com.example.users.controllers;

import com.example.users.models.dtos.UserRequest;
import com.example.users.models.dtos.UserResponse;
import com.example.users.services.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RequiredArgsConstructor
@RestController
@RequestMapping(path = "/api/users")
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<UserResponse>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{user_id}")
    public ResponseEntity<UserResponse> getUserById(@PathVariable("user_id") UUID id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @PutMapping("/{user_id}")
    public ResponseEntity<UserResponse> updateUser(@PathVariable ("user_id") UUID id,
                                             @Valid @RequestBody UserRequest userUpdates) {
        return ResponseEntity.ok(userService.updateUser(id, userUpdates));
    }

    @DeleteMapping("/{user_id}")
    public ResponseEntity<String> deleteUser(@PathVariable("user_id") UUID id) {
        userService.deleteUser(id);
        return ResponseEntity.ok("User successfully deleted");
    }

}