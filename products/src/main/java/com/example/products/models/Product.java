package com.example.products.models;

import com.example.commons.models.BaseEntity;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "products")
public class Product extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @Column(name = "image_id", unique = true)
    private UUID imageId;

    @Column(name = "image_url", unique = true)
    private String imageUrl;

    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "author_id")
    private UUID authorId;

    @Column(name = "genre_id")
    private UUID genreId;
}
