package com.example.orders.models.dtos;

import com.example.orders.models.OrderStatus;
import lombok.Data;
import org.springframework.stereotype.Component;

import jakarta.validation.constraints.*;

@Component
@Data
public class OrderStatusUpdateDTO {

    @NotNull(message = "Status cannot be null")
    private OrderStatus status;

}
