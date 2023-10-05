package com.example.payments.client;

import com.example.orders.models.dtos.OrderStatusUpdateDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.UUID;

@FeignClient(name = "order-service", url = "${order.service.url}")
public interface OrderClient {
    @PutMapping("/api/users/{user_id}/orders/{order_id}/status")
    ResponseEntity<String> updateOrderStatus(
            @PathVariable("user_id") UUID userId,
            @PathVariable("order_id") UUID orderId,
            @RequestBody OrderStatusUpdateDTO statusUpdateDTO);

    @GetMapping("/api/users/{user_id}/orders/{order_id}")
    ResponseEntity<Void> checkOrderExistence(@PathVariable("user_id") UUID userId,
                                             @PathVariable ("order_id") UUID orderId);
}


