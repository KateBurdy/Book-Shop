package com.example.authors;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.example.authors, com.example.commons"})
public class AuthorsApplication {

    public static void main(String[] args) {
        SpringApplication.run(AuthorsApplication.class, args);
    }

}
