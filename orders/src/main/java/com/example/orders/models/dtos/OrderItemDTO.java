package com.example.orders.models.dtos;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderItemDTO {
    @NotNull(message = "ProductId cannot be null")
    private UUID productId;

    @NotNull(message = "Quantity cannot be null")
    private Integer quantity;

    private BigDecimal discount;

    @NotNull(message = "PricePerUnit cannot be null")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal pricePerUnit;

    private BigDecimal totalPrice;
}
