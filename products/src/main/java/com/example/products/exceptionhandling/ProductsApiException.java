package com.example.products.exceptionhandling;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
class ProductsApiException {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}
