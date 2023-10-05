package com.example.authors.repositories;

import com.example.authors.models.Author;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface AuthorRepository extends JpaRepository<Author, UUID> {
    Boolean existsByNameAndDeletedAtIsNull(String name);

    List<Author> findAllByDeletedAtIsNull();

    Optional<Author> findByIdAndDeletedAtIsNull(UUID id);

}

