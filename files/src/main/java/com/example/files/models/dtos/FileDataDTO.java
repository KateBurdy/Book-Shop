package com.example.files.models.dtos;


import lombok.Data;

import java.util.UUID;

@Data
public class FileDataDTO {
    private UUID id;
    private byte[] data;
    private String filename;
}
