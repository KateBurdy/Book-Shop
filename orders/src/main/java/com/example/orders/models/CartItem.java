package com.example.orders.models;

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
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "cart_items")
public class CartItem extends BaseEntity {

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;

    @Column(name = "product_id")
    private UUID productId;

    @Column(name="quantity")
    private int quantity;

    @Column(name="price_per_unit")
    private BigDecimal pricePerUnit;

    @Column(name="discount")
    private BigDecimal discount;

    @Column(name="total_price")
    private BigDecimal totalPrice;
}
