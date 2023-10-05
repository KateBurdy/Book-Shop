package com.example.payments.mappers;

import com.example.payments.models.dtos.PaymentDTO;
import com.example.payments.models.dtos.PaymentRequest;
import com.example.payments.models.Payment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Mapper(componentModel = "spring")
public interface PaymentMapper {
    @Mapping(target = "totalPrice", source = "request.totalPrice")
    @Mapping(target = "orderId", source = "orderId")
    @Mapping(target = "paymentUrl", expression = "java(paymentUrl(userId, orderId, request, appBaseUrl, "
                                                + "paymentProviderUrl))")
    @Mapping(target = "status", constant = "PENDING")
    Payment fromRequest(UUID userId, UUID orderId, PaymentRequest request, String appBaseUrl, String paymentProviderUrl);

    default String paymentUrl(UUID userId, UUID orderId, PaymentRequest request, String appBaseUrl,
                              String paymentProviderUrl)
    {
        String callbackUrl = String.format("%s/api/users/%s/orders/%s/payments/success", appBaseUrl, userId, orderId);
        return paymentProviderUrl + "?callback_url=" + URLEncoder.encode(callbackUrl, StandardCharsets.UTF_8)
                + "&price=" + request.getTotalPrice();
    }

    PaymentDTO toPaymentDTO(Payment payment);
}
