package com.example.orders.exceptionhandling;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
class OrdersApiException {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}
