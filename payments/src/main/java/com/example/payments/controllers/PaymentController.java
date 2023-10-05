package com.example.payments.controllers;

import com.example.payments.models.dtos.PaymentDTO;
import com.example.payments.models.dtos.PaymentRequest;
import com.example.payments.services.PaymentService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api/users/{user_id}/orders/{order_id}/payments")
public class PaymentController {

    private final PaymentService paymentService;

    @PostMapping
    public ResponseEntity<PaymentDTO> createPayment(
            @PathVariable ("user_id") UUID userId,
            @PathVariable ("order_id") UUID orderId,
            @Valid @RequestBody PaymentRequest request) {

        PaymentDTO response = paymentService.createPayment(userId, orderId, request);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/success")
    public ResponseEntity<PaymentDTO> handlePaymentSuccess(
            @PathVariable ("user_id") UUID userId,
            @PathVariable ("order_id") UUID orderId) {

        PaymentDTO paymentDTO = paymentService.handlePaymentSuccess(userId, orderId);
        return ResponseEntity.ok(paymentDTO);
    }

}
