package com.example.orders.repositories;

import com.example.orders.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository extends JpaRepository<Cart, UUID> {
    Cart findByUserId(UUID userId);
}
