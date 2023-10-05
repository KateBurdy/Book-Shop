package com.example.files.models.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public class FileDTO {
    private UUID id;
    private String filename;
    private String contentType;
}

