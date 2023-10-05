package com.example.files;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableJpaAuditing
@EntityScan(basePackages = {"com.example.files, com.example.commons"})
@EnableTransactionManagement
public class FilesApplication {

    public static void main(String[] args) {
        SpringApplication.run(FilesApplication.class, args);
    }

}
