package com.example.payments.models;

import com.example.commons.models.BaseEntity;
import jakarta.persistence.*;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "payments")
public class Payment extends BaseEntity {

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "order_id")
    private UUID orderId;

    @Column(name = "total_price")
    private BigDecimal totalPrice;

    @Column(name = "payment_url")
    private String paymentUrl;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private PaymentStatus status;

}

