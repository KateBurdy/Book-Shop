package com.example.orders.controllers;

import com.example.orders.models.dtos.OrderDTO;
import com.example.orders.models.dtos.OrderItemDTO;
import com.example.orders.models.dtos.OrderStatusUpdateDTO;
import com.example.orders.services.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users/{user_id}/orders")
public class OrderController {

    private final OrderService orderService;
    @GetMapping
    public List<OrderDTO> getUserOrders(@PathVariable("user_id") UUID userId) {
        return orderService.getUserOrders(userId);
    }

    @GetMapping("{order_id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable ("order_id") UUID orderId) {
        OrderDTO result = orderService.getOrder(orderId);
        return ResponseEntity.ok(result);
    }

    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(@PathVariable("user_id") UUID userId,
                                @Valid @RequestBody List<OrderItemDTO> orderItemDTOs) {
        OrderDTO result = orderService.createOrder(userId, orderItemDTOs);
        return ResponseEntity.ok(result);
    }

    @PutMapping("/{order_id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(@PathVariable("user_id") UUID userId,
                                                    @PathVariable("order_id") UUID orderId,
                                                    @Valid @RequestBody OrderStatusUpdateDTO statusUpdateDTO) {
        OrderDTO result = orderService.updateOrderStatus(orderId, userId, statusUpdateDTO);
        return ResponseEntity.ok(result);
    }
}

