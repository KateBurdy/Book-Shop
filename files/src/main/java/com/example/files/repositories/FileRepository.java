package com.example.files.repositories;

import com.example.files.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface FileRepository extends JpaRepository<FileEntity, UUID> {
    Optional<FileEntity> findByFilename(String filename);

}
