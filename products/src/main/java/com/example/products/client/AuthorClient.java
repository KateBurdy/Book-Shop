package com.example.products.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@FeignClient(name = "author-service", url = "${author.service.url}")
public interface AuthorClient {
    @GetMapping("/api/authors/{author_id}")
    ResponseEntity<Void> checkAuthorExists(@PathVariable("author_id") UUID id);
}