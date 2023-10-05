package com.example.orders.services;

import com.example.orders.client.ProductServiceClient;
import com.example.orders.models.OrderStatus;
import com.example.orders.models.dtos.*;
import com.example.orders.models.Order;
import com.example.orders.models.OrderItem;
import com.example.orders.exceptions.*;
import com.example.orders.mappers.OrderItemMapper;
import com.example.orders.mappers.OrderMapper;
import com.example.orders.repositories.OrderItemRepository;
import com.example.orders.repositories.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.example.orders.models.OrderStatus.NEW;

@Service
@Slf4j
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final OrderMapper orderMapper;
    private final ProductServiceClient productServiceClient;

    public List<OrderDTO> getUserOrders(UUID userId) {
        List<Order> orders = getOrdersByUserId(userId);
        return orders.stream()
                .map(this::mapToOrderDTO)
                .collect(Collectors.toList());
    }

    public OrderDTO getOrder(UUID orderId) {
        Order order = validateAndGetOrder(orderId);
        return mapToOrderDTO(order);
    }

    @Transactional
    public OrderDTO createOrder(UUID userId, List<OrderItemDTO> orderItemDTOs) {
        validateProductIds(orderItemDTOs);
        Order order = buildAndSaveOrder(userId);
        List<OrderItem> savedOrderItems = saveOrderItems(order, orderItemDTOs);
        order.setOrderItems(savedOrderItems);
        return buildOrderDTO(order, orderItemDTOs);
    }


    public OrderDTO updateOrderStatus(UUID orderId, UUID userId, OrderStatusUpdateDTO statusUpdateDTO) {
        log.debug("Updating order status to: {} with orderId: {}", statusUpdateDTO.getStatus(), orderId);
        Order order = validateAndGetOrder(orderId, userId);
        updateAndSaveOrderStatus(order, statusUpdateDTO.getStatus());

        return mapToOrderDTO(order);
    }

    private List<Order> getOrdersByUserId(UUID userId) {
        return Optional.ofNullable(orderRepository.findByUserId(userId))
                .filter(orders -> !orders.isEmpty())
                .orElseThrow(() -> new OrderNotFoundException(userId));
    }

    private Order buildAndSaveOrder(UUID userId) {
        Order order = Order.builder()
                .userId(userId)
                .createdOn(LocalDateTime.now())
                .status(NEW)
                .build();
        return orderRepository.save(order);
    }

    private OrderDTO buildOrderDTO(Order order, List<OrderItemDTO> orderItemDTOs) {
        OrderDTO orderDTO = mapToOrderDTO(order);
        orderDTO.setOrderItems(orderItemDTOs);
        return orderDTO;
    }

    private List<OrderItem> saveOrderItems(Order order, List<OrderItemDTO> orderItemDTOs) {
        return orderItemDTOs.stream()
                .map(dto -> orderItemMapper.fromDto(dto, order))
                .map(orderItemRepository::save)
                .collect(Collectors.toList());
    }


    private void validateProductIds(List<OrderItemDTO> orderItemDTOs) {
        orderItemDTOs.stream()
                .map(OrderItemDTO::getProductId)
                .forEach(this::validateProduct);
    }

    private void updateAndSaveOrderStatus(Order order, OrderStatus newStatus) {
        order.setStatus(newStatus);
        orderRepository.save(order);
    }

    private Order validateAndGetOrder(UUID orderId) {
        return orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private Order validateAndGetOrder(UUID orderId, UUID userId) {
        return orderRepository.findByIdAndUserId(orderId, userId)
                .orElseThrow(() -> new OrderNotFoundException(orderId));
    }

    private OrderDTO mapToOrderDTO(Order order) {
        List<OrderItemDTO> orderItems = getValidOrderItems(order);
        OrderDTO orderDTO = orderMapper.toDTO(order);
        orderDTO.setOrderItems(orderItems);
        return orderDTO;
    }

    private List<OrderItemDTO> getValidOrderItems(Order order) {
        return Optional.ofNullable(order.getOrderItems())
                .filter(items -> !items.isEmpty())
                .orElseThrow(() -> new OrderItemNotFoundException(order.getId()))
                .stream()
                .map(orderItemMapper::toDto)
                .collect(Collectors.toList());
    }

    private void validateProduct(UUID productId) {
        if (!doesProductExist(productId)) {
            throw new ProductNotFoundException(productId);
        }
    }

    private boolean doesProductExist(UUID productId) {
        ResponseEntity<Void> response = productServiceClient.checkProductExistence(productId);
        return response.getStatusCode() == HttpStatus.OK;
    }
}


