package com.example.products.repositories;


import com.example.products.models.Genre;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GenreRepository extends JpaRepository<Genre, UUID> {
    boolean existsByNameAndDeletedAtIsNull(String name);

    List<Genre> findAllByDeletedAtIsNull();

    Optional<Genre> findByIdAndDeletedAtIsNull(UUID genreId);

    boolean existsByName(String genreName);
}

