package com.example.files.mappers;

import com.example.files.models.FileDataEntity;
import com.example.files.models.dtos.FileDataDTO;
import org.mapstruct.Mapper;

@Mapper (componentModel = "spring")
public interface FileDataMapper {

    FileDataDTO toFileDataDTO(FileDataEntity fileDataEntity);
}
