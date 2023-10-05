package com.example.products.services;

import com.example.products.exceptions.GenreNameAlreadyExistsException;
import com.example.products.exceptions.GenreNotFoundException;
import com.example.products.mappers.GenreMapper;
import com.example.products.models.Genre;
import com.example.products.models.dtos.GenreDTO;
import com.example.products.repositories.GenreRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GenreService {

    private final GenreRepository genreRepository;
    private final GenreMapper genreMapper;

    public GenreDTO createGenre(GenreDTO genreDTO) {
        if (genreRepository.existsByNameAndDeletedAtIsNull(genreDTO.getName())) {
            throw new GenreNameAlreadyExistsException(genreDTO.getName());
        }
        Genre genre = genreMapper.toGenre(genreDTO);
        genre = genreRepository.save(genre);
        return genreMapper.toGenreDTO(genre);
    }

    public void deleteGenre(UUID id) {
        Genre genre = ensureGenreExits(id);
        genre.setDeletedAt(Instant.now());
        genreRepository.save(genre);
    }

    public List<GenreDTO> getAllGenres() {
        List<Genre> genres = genreRepository.findAllByDeletedAtIsNull();
        return genres.stream().map(genreMapper::toGenreDTO).collect(Collectors.toList());
    }

    public GenreDTO getGenreById(UUID id) {
        Genre genre = ensureGenreExits(id);
        return genreMapper.toGenreDTO(genre);
    }

    private Genre ensureGenreExits(UUID genreId){
        return genreRepository.findByIdAndDeletedAtIsNull(genreId).orElseThrow(() -> new GenreNotFoundException(genreId));
    }
}
