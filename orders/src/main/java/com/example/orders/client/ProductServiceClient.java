package com.example.orders.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;


@FeignClient(name = "product-service", url = "${product.service.url}")
public interface ProductServiceClient {
    @GetMapping("/api/products/{product_id}")
    ResponseEntity<Void> checkProductExistence(@PathVariable("product_id") UUID productId);

}

