package com.example.products.mappers;


import com.example.products.models.dtos.ProductRequest;
import com.example.products.models.dtos.ProductResponse;
import com.example.products.models.Product;
import com.example.products.utils.ImageUrlProvider;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {

    @Autowired
    private ImageUrlProvider imageUrlProvider;

    @Mapping(target = "imageId", source = "productRequest.imageId")
    @Mapping(target = "imageUrl", source = "productRequest.imageId", qualifiedByName = "imageUrl")
    @Mapping(target = "genreId", source = "productRequest.genreId")
    @Mapping(target = "authorId", source = "productRequest.authorId")
    public abstract Product toProduct(ProductRequest productRequest);

    @Mapping(target = "genreId", source = "product.genreId")
    @Mapping(target = "authorId", source = "product.authorId")
    public abstract ProductResponse toProductResponse(Product product);

    @Named("imageUrl")
    protected String imageUrl(UUID imageId) {
        return imageUrlProvider.getImageUrl(imageId);
    }

}

