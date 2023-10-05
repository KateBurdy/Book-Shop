package com.example.files.mappers;


import com.example.files.models.FileEntity;
import com.example.files.models.dtos.FileDTO;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface FileMapper {

    FileDTO toFileDTO(FileEntity fileEntity);
}