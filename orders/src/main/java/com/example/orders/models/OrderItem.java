package com.example.orders.models;

import com.example.commons.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "order_items")
public class OrderItem extends BaseEntity {

    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    private Order order;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name="price_per_unit")
    private BigDecimal pricePerUnit;

    @Column(name="discount")
    private BigDecimal discount;

    @Column(name="total_price")
    private BigDecimal totalPrice;

}
