package com.example.payments.services;

import com.example.orders.models.OrderStatus;
import com.example.orders.models.dtos.OrderStatusUpdateDTO;
import com.example.payments.client.OrderClient;
import com.example.payments.exceptions.OrderDoesNotExist;
import com.example.payments.exceptions.OrderStatusUpdateException;
import com.example.payments.exceptions.PaymentAlreadyExistsException;
import com.example.payments.exceptions.PaymentNotFoundException;
import com.example.payments.mappers.PaymentMapper;
import com.example.payments.models.Payment;
import com.example.payments.models.dtos.PaymentDTO;
import com.example.payments.models.dtos.PaymentRequest;
import com.example.payments.repositories.PaymentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

import static com.example.payments.models.PaymentStatus.SUCCESS;

@Service
@Slf4j
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Value("${payment.provider.url}")
    private String paymentProviderUrl;

    @Value("${svc.gateway.base_url}")
    private String appBaseUrl;
    private final PaymentMapper paymentMapper;
    private final OrderClient orderClient;

    public PaymentDTO createPayment(UUID userId, UUID orderId, PaymentRequest request) {
        validateOrder(userId, orderId);
        validatePayment(userId, orderId);
        Payment payment = paymentMapper.fromRequest(userId, orderId, request, appBaseUrl, paymentProviderUrl);
        log.debug("Creating payment: {}", payment);
        paymentRepository.save(payment);
        return paymentMapper.toPaymentDTO(payment);
    }
    public PaymentDTO handlePaymentSuccess(UUID userId, UUID orderId) {
        Payment payment = ensurePaymentExists(userId, orderId);
        payment.setStatus(SUCCESS);
        paymentRepository.save(payment);

        OrderStatusUpdateDTO statusUpdateDTO = new OrderStatusUpdateDTO();
        statusUpdateDTO.setStatus(OrderStatus.PAID);
        ResponseEntity<String> response = orderClient.updateOrderStatus(userId, orderId, statusUpdateDTO);

        if (response.getStatusCode() != HttpStatus.OK) {
            throw new OrderStatusUpdateException(orderId, response.getBody());
        }

        return paymentMapper.toPaymentDTO(payment);
    }


    private void validatePayment(UUID userId, UUID orderId) {
        Optional<Payment> existingPayment = paymentRepository.findByUserIdAndOrderId(userId, orderId);
        if (existingPayment.isPresent()) {
            throw new PaymentAlreadyExistsException(userId, orderId);
        }
    }

    public Payment ensurePaymentExists(UUID userId, UUID orderId) {
        return paymentRepository.findByUserIdAndOrderId(userId, orderId)
                .orElseThrow(() -> new PaymentNotFoundException(userId, orderId));
    }

    public void validateOrder(UUID userId, UUID orderId) {
        ResponseEntity<Void> response = orderClient.checkOrderExistence(userId, orderId);
        if (response.getStatusCode() != HttpStatus.OK) {
            throw new OrderDoesNotExist(orderId);
        }
    }
}
