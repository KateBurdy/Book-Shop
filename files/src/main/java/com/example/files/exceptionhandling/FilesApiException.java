package com.example.files.exceptionhandling;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Builder
public class FilesApiException {
    private String timestamp;
    private int status;
    private String error;
    private String message;
}
