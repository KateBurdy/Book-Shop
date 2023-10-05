package com.example.authors.models.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    private String description;
}
