package com.example.orders.controllers;

import com.example.orders.models.dtos.CartDTO;
import com.example.orders.models.dtos.CartRequestDTO;
import com.example.orders.services.CartService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/users/{user_id}/orders/cart")
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<CartDTO> getCartByUserId(@PathVariable("user_id") UUID userId) {
        CartDTO response = cartService.getCartByUserId(userId);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{product_id}")
    public ResponseEntity<CartDTO> addItemToCart(@PathVariable("user_id") UUID userId,
                                              @PathVariable("product_id") UUID productId,
                                              @Valid @RequestBody CartRequestDTO cartRequest) {
        CartDTO response = cartService.addCartItem(userId, productId, cartRequest);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{product_id}")
    public ResponseEntity<CartDTO> updateProductQuantityInCartItem(@PathVariable("user_id") UUID userId,
                                                                   @PathVariable("product_id") UUID productId,
                                                                   @Valid @RequestBody CartRequestDTO cartRequest) {
        CartDTO response = cartService.updateProductQuantityInCartItem(userId, productId, cartRequest);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{product_id}")
    public ResponseEntity<CartDTO> removeProductFromCart (@PathVariable("user_id") UUID userId,
                                                          @PathVariable("product_id") UUID productId){
        CartDTO response = cartService.removeProductFromCart(userId, productId);
        return ResponseEntity.ok(response);
    }
}
