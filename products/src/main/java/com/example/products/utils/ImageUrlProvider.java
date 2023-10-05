package com.example.products.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class ImageUrlProvider {

    @Value("${base.url}")
    private String baseUrl;

    public String getImageUrl(UUID imageId) {
        return baseUrl + imageId.toString();
    }
}