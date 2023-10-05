package com.example.authentication.exceptionhandling;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class AuthApiException {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}
