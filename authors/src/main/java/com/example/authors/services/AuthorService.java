package com.example.authors.services;


import com.example.authors.exceptions.AuthorNameAlreadyExistsException;
import com.example.authors.models.Author;
import com.example.authors.repositories.AuthorRepository;
import com.example.authors.mappers.AuthorMapper;
import com.example.authors.exceptions.AuthorNotFoundException;
import com.example.authors.models.dtos.AuthorDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthorService {
    private final AuthorRepository authorRepository;
    private final AuthorMapper authorMapper;

    public List<AuthorDTO> getAllAuthors() {
        List<Author> authors = authorRepository.findAllByDeletedAtIsNull();
        return authors.stream()
                .map(authorMapper::toAuthorDTO)
                .collect(Collectors.toList());
    }

    public AuthorDTO getAuthorById(UUID id) {
        Author author = ensureAuthorExists(id);
        return authorMapper.toAuthorDTO(author);
    }

    public AuthorDTO createAuthor(AuthorDTO authorDTO) {
        ensureAuthorNameIsUnique(authorDTO.getName());
        Author author = authorMapper.toAuthor(authorDTO);
        Author savedAuthor = authorRepository.save(author);
        return authorMapper.toAuthorDTO(savedAuthor);
    }

    public AuthorDTO updateAuthor(UUID id, AuthorDTO authorDTO) {
        Author existingAuthor = ensureAuthorExists(id);
        ensureAuthorNameIsUnique(authorDTO.getName());

        existingAuthor.setName(authorDTO.getName());
        existingAuthor.setDescription(authorDTO.getDescription());

        return authorMapper.toAuthorDTO(existingAuthor);
    }

    public void deleteAuthor(UUID id) {
        Author author = ensureAuthorExists(id);

        author.setDeletedAt(Instant.now());
        authorRepository.save(author);
    }

    private void ensureAuthorNameIsUnique(String name) {
        if (authorRepository.existsByNameAndDeletedAtIsNull(name)) {
            throw new AuthorNameAlreadyExistsException(name);
        }
    }

    private Author ensureAuthorExists(UUID id) {
        return authorRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> new AuthorNotFoundException(id));
    }

}
