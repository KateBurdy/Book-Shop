package com.example.products.services;

import com.example.products.client.AuthorClient;
import com.example.products.exceptions.*;
import com.example.products.models.Product;
import com.example.products.repositories.GenreRepository;
import com.example.products.repositories.ProductRepository;
import com.example.products.models.dtos.ProductRequest;
import com.example.products.models.dtos.ProductResponse;
import com.example.products.mappers.ProductMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    private final GenreRepository genreRepository;
    private final AuthorClient authorClient;


    public List<ProductResponse> getProducts() {
        List<Product> products = productRepository.findAllByDeletedAtIsNull();
        return products.stream()
                .map(this::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse getProductById(UUID id) {
        Product product = ensureProductExists(id);
        return toProductResponse(product);
    }
    public List<ProductResponse> getProductsByAuthorId(UUID authorId) {
        List <Product> products= productRepository.findByAuthorIdAndDeletedAtIsNull(authorId);
        return products.stream()
                .map(productMapper::toProductResponse)
                .collect(Collectors.toList());
    }

    public ProductResponse createProduct(ProductRequest productRequest) {
        validateProduct(productRequest);
        doesAuthorExist(productRequest.getAuthorId());
        validateGenre(productRequest.getGenreId());

        Product product = productMapper.toProduct(productRequest);
        product = productRepository.save(product);

        return toProductResponse(product);
    }

    public void deleteProduct(UUID id) {
        Product product = ensureProductExists(id);

        product.setDeletedAt(Instant.now());
        productRepository.save(product);
    }

    private void validateGenre(UUID id) {
        if (!genreRepository.existsById(id)) {
            throw new GenreNameDoesNotExistException(id);
        }
    }

    private void doesAuthorExist(UUID authorId) {
        ResponseEntity<Void> response = authorClient.checkAuthorExists(authorId);
        if (response.getStatusCode() != HttpStatus.OK){
            throw new AuthorNotFoundException(authorId);
        }
    }
    private void validateProduct(ProductRequest productRequest) {
        if (productRepository.existsByNameAndDeletedAtIsNull(productRequest.getName())) {
            throw new ProductNameAlreadyExistsException(productRequest.getName());
        }

        if (productRepository.existsByImageIdAndDeletedAtIsNull(productRequest.getImageId())){
            throw new ImageIdAlreadyExistsException(productRequest.getImageId());
        }
    }

    private Product ensureProductExists(UUID id) {
        return productRepository.findByIdAndDeletedAtIsNull(id)
                .orElseThrow(() -> (new ProductNotFoundException(id)));
    }

    private ProductResponse toProductResponse(Product product) {
        ProductResponse response = productMapper.toProductResponse(product);
        return response;
    }
}
