package com.example.payments.exceptionhandling;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
class PaymentsApiException {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}
