package com.example.products.models.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class GenreDTO {
    @NotBlank(message = "Name cannot be blank")
    private String name;
    @NotBlank(message = "Description cannot be blank")
    private String description;
}