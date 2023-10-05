package com.example.products.models.dtos;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Data
@NoArgsConstructor
public class ProductResponse {
    private String name;
    private String description;
    private String imageUrl;
    private BigDecimal price;
    private UUID authorId;
    private UUID genreId;
}
