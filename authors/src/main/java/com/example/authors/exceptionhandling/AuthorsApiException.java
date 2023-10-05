package com.example.authors.exceptionhandling;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
class AuthorsApiException {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}
