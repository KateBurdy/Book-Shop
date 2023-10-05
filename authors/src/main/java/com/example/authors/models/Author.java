package com.example.authors.models;

import com.example.commons.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "authors")
public class Author extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;
}