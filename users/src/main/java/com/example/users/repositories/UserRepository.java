package com.example.users.repositories;

import com.example.users.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    List<User> findAllByDeletedAtIsNull();
    Optional<User> findByIdAndDeletedAtIsNull(UUID id);

    Optional <User> findByUsername(String username);

}
