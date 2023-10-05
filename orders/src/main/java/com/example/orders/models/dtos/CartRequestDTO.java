package com.example.orders.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;
import java.math.BigDecimal;

@Data
@NoArgsConstructor
public class CartRequestDTO {
    @NotNull(message = "Quantity cannot be null")
    @Min(1)
    private int quantity;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
}