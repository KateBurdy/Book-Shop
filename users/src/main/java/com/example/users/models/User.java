package com.example.users.models;

import com.example.commons.models.BaseEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User extends BaseEntity {

    @Column(name = "username", unique = true)
    private String username;

    @Column(name = "password")
    private String password;

}
