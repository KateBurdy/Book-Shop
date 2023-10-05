package com.example.products.controllers;

import com.example.products.models.dtos.GenreDTO;
import com.example.products.services.GenreService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products/genres")
public class GenreController {

    private final GenreService genreService;

    @PostMapping
    public ResponseEntity<GenreDTO> createGenre(@Valid @RequestBody GenreDTO genreDTO) {
        GenreDTO created = genreService.createGenre(genreDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteGenre(@PathVariable UUID id) {
        genreService.deleteGenre(id);
        return ResponseEntity.ok("Genre successfully deleted");
    }

    @GetMapping
    public ResponseEntity<List<GenreDTO>> getAllGenres() {
        return ResponseEntity.ok(genreService.getAllGenres());
    }

    @GetMapping("/{id}")
    public ResponseEntity<GenreDTO> getGenreById(@PathVariable UUID id) {
        return ResponseEntity.ok(genreService.getGenreById(id));
    }
}
