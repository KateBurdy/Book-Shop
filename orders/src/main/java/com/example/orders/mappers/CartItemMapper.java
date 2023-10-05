package com.example.orders.mappers;

import com.example.orders.models.CartItem;
import com.example.orders.models.dtos.CartItemDTO;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public interface CartItemMapper {
    CartItemDTO cartItemToCartItemDTO(CartItem cartItem);
    List<CartItemDTO> cartItemsToCartItemDTOs(List<CartItem> cartItems);
}
