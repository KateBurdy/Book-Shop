package com.example.orders.models.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
@Builder
@AllArgsConstructor
public class CartItemDTO {
    private UUID productId;
    private int quantity;
    private BigDecimal pricePerUnit;
    private BigDecimal discount;
    private BigDecimal totalPrice;
}
