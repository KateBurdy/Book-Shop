package com.example.payments.models.dtos;

import com.example.payments.models.PaymentStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.UUID;

@Data
public class PaymentDTO {
    private UUID userId;
    private UUID orderId;
    private BigDecimal totalPrice;
    private String paymentUrl;
    private PaymentStatus status;
}
