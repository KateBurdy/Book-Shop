package com.example.products.models;

import com.example.commons.models.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "genres")
public class Genre extends BaseEntity {

    @Column(name = "name", unique = true)
    private String name;

    @Column(name = "description")
    private String description;

    @OneToMany(mappedBy = "genreId")
    private List<Product> products;

}
