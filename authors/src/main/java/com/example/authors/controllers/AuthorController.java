package com.example.authors.controllers;

import com.example.authors.services.AuthorService;
import com.example.authors.models.dtos.AuthorDTO;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/authors")
public class AuthorController {
    private final AuthorService authorService;

    @GetMapping
    public ResponseEntity<List<AuthorDTO>> getAllAuthors() {
        return ResponseEntity.ok(authorService.getAllAuthors());
    }

    @GetMapping("/{author_id}")
    public ResponseEntity<AuthorDTO> getAuthorById(@PathVariable("author_id") UUID id) {
        return ResponseEntity.ok(authorService.getAuthorById(id));
    }

    @PostMapping
    public ResponseEntity<AuthorDTO> createAuthor(@Valid @RequestBody AuthorDTO authorDTO) {
        return new ResponseEntity<>(authorService.createAuthor(authorDTO), HttpStatus.CREATED);
    }

    @PutMapping("/{author_id}")
    public ResponseEntity<AuthorDTO> updateAuthor(@PathVariable("author_id") UUID id, @Valid @RequestBody AuthorDTO authorDTO) {
        return ResponseEntity.ok(authorService.updateAuthor(id, authorDTO));
    }

    @DeleteMapping("/{author_id}")
    public ResponseEntity<String> deleteAuthor(@PathVariable("author_id") UUID id) {
        authorService.deleteAuthor(id);
        return ResponseEntity.ok("Author successfully deleted");
    }
}
