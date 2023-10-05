package com.example.files.repositories;


import com.example.files.models.FileDataEntity;
import com.example.files.models.FileEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FileDataRepository extends JpaRepository<FileDataEntity, UUID> {
    Optional<FileDataEntity> findByFileId(FileEntity fileEntity);
}
