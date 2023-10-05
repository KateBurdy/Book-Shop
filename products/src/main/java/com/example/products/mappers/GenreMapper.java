package com.example.products.mappers;

import com.example.products.models.Genre;
import com.example.products.models.dtos.GenreDTO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GenreMapper {
    Genre toGenre(GenreDTO genreDTO);
    GenreDTO toGenreDTO(Genre genre);
}
