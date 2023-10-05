package com.example.files.controllers;


import com.example.files.models.dtos.FileDTO;
import com.example.files.models.dtos.FileDataDTO;
import com.example.files.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/files")
public class FileController {

    private final FileService fileService;

    @GetMapping("/{file_id}")
    public ResponseEntity<byte[]> downloadFile(@PathVariable ("file_id") UUID fileId) {
        FileDataDTO fileDataDTO = fileService.getFileData(fileId);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\""
                        + fileDataDTO.getFilename() + "\"")
                .body(fileDataDTO.getData());
    }

    @PostMapping
    public UUID uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        FileDTO fileDTO = fileService.uploadFile(file);

        return fileDTO.getId();
    }

    @PutMapping("/{file_id}")
    public ResponseEntity<String> replaceFile(@PathVariable ("file_id") UUID fileId,
                                              @RequestParam("file") MultipartFile file) throws IOException {
        fileService.replaceFile(fileId, file);
        return ResponseEntity.ok("File updated successfully");
    }
}
