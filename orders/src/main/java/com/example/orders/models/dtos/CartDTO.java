package com.example.orders.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

import java.util.List;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CartDTO {
    private UUID userId;
    private List<CartItemDTO> cartItems;
}
