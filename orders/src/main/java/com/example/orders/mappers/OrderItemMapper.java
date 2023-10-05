package com.example.orders.mappers;

import com.example.orders.models.dtos.OrderItemDTO;
import com.example.orders.models.Order;
import com.example.orders.models.OrderItem;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {

    public abstract OrderItem fromDto(OrderItemDTO orderItemDTO, Order order);

    public abstract OrderItemDTO toDto(OrderItem orderItem);

    @AfterMapping
    protected void setOrder(OrderItemDTO orderItemDTO, @MappingTarget OrderItem orderItem, Order order) {
        orderItem.setOrder(order);
    }
}
