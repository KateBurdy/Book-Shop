package com.example.users.services;

import com.example.users.models.dtos.UserRequest;
import com.example.users.models.dtos.UserResponse;
import com.example.users.exceptions.UserNotFoundException;
import com.example.users.exceptions.UsernameAlreadyExistsException;
import com.example.users.mappers.UserMapper;
import com.example.users.models.User;
import com.example.users.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public List<UserResponse> getAllUsers() {
        List<User> users = userRepository.findAllByDeletedAtIsNull();
        return users.stream()
                .map(userMapper::mapToUserResponseDTO)
                .collect(Collectors.toList());
    }

    public UserResponse getUserById(UUID id) {
        User user = ensureUserExists(id);
        return userMapper.mapToUserResponseDTO(user);
    }

    public UserResponse updateUser(UUID id, UserRequest userUpdates) {

        validateUser(userUpdates.getUsername());

        User user = ensureUserExists(id);

        user.setUsername(userUpdates.getUsername());
        userRepository.save(user);

        return userMapper.mapToUserResponseDTO(user);
    }

    public void deleteUser(UUID id) {
        User user = ensureUserExists(id);

        user.setDeletedAt(Instant.now());
        userRepository.save(user);
    }

    public User ensureUserExists(UUID id) {
        return userRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() ->
                        new UserNotFoundException(id)
                );
    }

    private void validateUser(String userName) {
        Optional <User> existingUser=userRepository.findByUsername(userName);
        if (existingUser.isPresent()) {
            throw new UsernameAlreadyExistsException(userName);
        }
    }
}
