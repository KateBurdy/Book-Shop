package com.example.products.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
public class ProductRequest {

    @NotBlank(message = "Name cannot be blank")
    private String name;

    @Size(max = 300, message = "Description cannot be longer than 200 characters")
    @NotBlank(message = "Description cannot be blank")
    private String description;

    @NotNull(message = "ImageId cannot be null")
    private UUID imageId;

    @NotNull(message = "Price cannot be null")
    @DecimalMin(value = "0.0", inclusive = false)
    @Digits(integer = 10, fraction = 2)
    private BigDecimal price;
    @NotNull(message = "Author id cannot be null")
    private UUID authorId;
    @NotNull(message = "Genre id cannot be null")
    private UUID genreId;
}
