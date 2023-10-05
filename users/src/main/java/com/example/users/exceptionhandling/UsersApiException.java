package com.example.users.exceptionhandling;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
class UsersApiException {
    private String timestamp;
    private int status;
    private String error;
    private String message;
    private String developerMessage;
}
