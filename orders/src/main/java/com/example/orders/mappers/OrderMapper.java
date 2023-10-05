package com.example.orders.mappers;

import com.example.orders.models.dtos.OrderDTO;
import com.example.orders.models.Order;
import org.mapstruct.Mapper;


@Mapper (componentModel = "spring")
public interface OrderMapper {
    OrderDTO toDTO(Order order);
}
