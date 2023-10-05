package com.example.orders.models.dtos;

import com.example.orders.models.OrderStatus;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Data
@NoArgsConstructor
public class OrderDTO {
    private UUID id;
    private UUID userId;
    private LocalDateTime createdOn;
    private OrderStatus status;
    private List<OrderItemDTO> orderItems;
}

