package com.example.products.repositories;

import com.example.products.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository extends JpaRepository<Product, UUID> {
    boolean existsByNameAndDeletedAtIsNull(String name);
    boolean existsByImageIdAndDeletedAtIsNull(UUID ImageId);

    List<Product> findByAuthorIdAndDeletedAtIsNull(UUID authorId);

    Optional<Product> findByIdAndDeletedAtIsNull(UUID id);

    List<Product> findAllByDeletedAtIsNull();
}

