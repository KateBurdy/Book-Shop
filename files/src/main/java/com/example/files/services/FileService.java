package com.example.files.services;

import com.example.files.exceptions.FileDataNotFoundException;
import com.example.files.exceptions.FileNameAlreadyExistsException;
import com.example.files.exceptions.FileNotFoundException;
import com.example.files.mappers.FileDataMapper;
import com.example.files.mappers.FileMapper;
import com.example.files.models.FileDataEntity;
import com.example.files.models.FileEntity;
import com.example.files.models.dtos.FileDTO;
import com.example.files.models.dtos.FileDataDTO;
import com.example.files.repositories.FileDataRepository;
import com.example.files.repositories.FileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class FileService {

    private final FileRepository fileRepository;
    private final FileDataRepository fileDataRepository;
    private final FileMapper fileMapper;
    private final FileDataMapper fileDataMapper;


    public FileDataDTO getFileData(UUID id) {
        return getFileData(fetchFileEntity(id));
    }

    public FileDataDTO getFileData(FileEntity fileEntity) {
        log.info("Fetching file data for file ID {}", fileEntity.getId());
        FileDataEntity fileDataEntity = fetchFileDataEntity(fileEntity);
        return fileDataMapper.toFileDataDTO(fileDataEntity);
    }

    public FileDTO uploadFile(MultipartFile file) throws IOException {
        validateFilename(file);

        String originalFilename = file.getOriginalFilename();

        log.info("Uploading file with name {}", originalFilename);
        String fileName = StringUtils.cleanPath(originalFilename);

        FileEntity fileEntity = FileEntity.builder()
                .filename(fileName)
                .contentType(file.getContentType())
                .build();
        fileEntity = fileRepository.save(fileEntity);

        FileDataEntity fileDataEntity = FileDataEntity.builder()
                .data(file.getBytes())
                .fileId(fileEntity)
                .build();
        fileDataRepository.save(fileDataEntity);

        return fileMapper.toFileDTO(fileEntity);
    }

    @Transactional
    public void replaceFile(UUID id, MultipartFile file) throws IOException {
        validateFilename(file);
        log.info("Replacing file with ID {}", id);

        FileEntity fileEntity = fetchFileEntity(id);
        FileDataEntity fileDataEntity = fetchFileDataEntity(fileEntity);

        fileDataEntity.setData(file.getBytes());
        fileDataRepository.save(fileDataEntity);

        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        fileEntity.setFilename(fileName);
        fileEntity.setContentType(file.getContentType());
        fileRepository.save(fileEntity);
    }

    private void validateFilename(MultipartFile file) {

        String originalFilename = file.getOriginalFilename();

        if (fileRepository.findByFilename(originalFilename).isPresent()) {
            throw new FileNameAlreadyExistsException(originalFilename);
        }
    }

    private FileEntity fetchFileEntity(UUID id) {
        return fileRepository.findById(id)
                .orElseThrow(() -> new FileNotFoundException(id));
    }

    private FileDataEntity fetchFileDataEntity(FileEntity fileEntity) {
        return fileDataRepository.findByFileId(fileEntity)
                .orElseThrow(() -> new FileDataNotFoundException(fileEntity.getId()));
    }
}

