package com.example.authors.mappers;


import com.example.authors.models.Author;
import com.example.authors.models.dtos.AuthorDTO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AuthorMapper {
    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    Author toAuthor(AuthorDTO authorDTO);

    @Mapping(source = "name", target = "name")
    @Mapping(source = "description", target = "description")
    AuthorDTO toAuthorDTO(Author entity);
}
