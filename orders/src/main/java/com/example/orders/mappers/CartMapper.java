package com.example.orders.mappers;

import com.example.orders.models.dtos.CartDTO;
import com.example.orders.models.Cart;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface CartMapper {
    CartDTO cartToCartDTO(Cart cart);
}
