package com.mindsprint.agriconnect.service;


import com.mindsprint.agriconnect.dto.OrderDTO;
import com.mindsprint.agriconnect.entity.Order;
import com.mindsprint.agriconnect.entity.OrderStatus;
import com.mindsprint.agriconnect.error.DuplicateOrderException;
import com.mindsprint.agriconnect.error.OrderNotFoundException;
import com.mindsprint.agriconnect.mapper.OrderMapper;
import com.mindsprint.agriconnect.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO, String username) {
        log.info("Creating new order: {} by user: {}", orderDTO.getOrderNumber(), username);

        // Check for duplicate order number
        if (orderRepository.existsByOrderNumber(orderDTO.getOrderNumber())) {
            throw new DuplicateOrderException("Order with number " + orderDTO.getOrderNumber() + " already exists");
        }

        // Map DTO to Entity
        Order order = orderMapper.toEntity(orderDTO);
        order.setCreatedBy(username);
        order.setLastModifiedBy(username);
        order.setStatus(OrderStatus.PENDING);

        // Calculate total amount
        order.setTotalAmount(order.getUnitPrice().multiply(java.math.BigDecimal.valueOf(order.getQuantity())));

        // Save order
        Order savedOrder = orderRepository.save(order);
        log.info("Order created successfully with ID: {}", savedOrder.getId());

        return orderMapper.toDTO(savedOrder);
    }

    @Override
    public OrderDTO updateOrder(Long orderId, OrderDTO orderDTO, String username) {
        log.info("Updating order ID: {} by user: {}", orderId, username);

        Order existingOrder = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        // Update fields
        existingOrder.setProductName(orderDTO.getProductName());
        existingOrder.setQuantity(orderDTO.getQuantity());
        existingOrder.setUnitPrice(orderDTO.getUnitPrice());
        existingOrder.setTotalAmount(orderDTO.getUnitPrice().multiply(java.math.BigDecimal.valueOf(orderDTO.getQuantity())));
        existingOrder.setDescription(orderDTO.getNotes());
        existingOrder.setDeliveryAddress(orderDTO.getDeliveryAddress());
        existingOrder.setLastModifiedBy(username);

        Order updatedOrder = orderRepository.save(existingOrder);
        log.info("Order updated successfully: {}", orderId);

        return orderMapper.toDTO(updatedOrder);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderById(Long orderId) {
        log.info("Fetching order by ID: {}", orderId);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public OrderDTO getOrderByOrderNumber(String orderNumber) {
        log.info("Fetching order by order number: {}", orderNumber);
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with number: " + orderNumber));
        return orderMapper.toDTO(order);
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getAllOrders() {
        log.info("Fetching all orders");
        return orderRepository.findAll().stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByUserId(String userId) {
        log.info("Fetching orders for user: {}", userId);
        return orderRepository.findOrdersByUserId(userId).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByBuyerId(String buyerId) {
        log.info("Fetching orders for buyer: {}", buyerId);
        return orderRepository.findByBuyerId(buyerId).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersBySellerId(String sellerId) {
        log.info("Fetching orders for seller: {}", sellerId);
        return orderRepository.findBySellerId(sellerId).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByStatus(OrderStatus status) {
        log.info("Fetching orders with status: {}", status);
        return orderRepository.findByStatus(status).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<OrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        log.info("Fetching orders between {} and {}", startDate, endDate);
        return orderRepository.findOrdersByDateRange(startDate, endDate).stream()
                .map(orderMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Override
    public OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus, String username) {
        log.info("Updating order status for ID: {} to {}", orderId, newStatus);

        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with ID: " + orderId));

        order.setStatus(newStatus);
        order.setLastModifiedBy(username);

        Order updatedOrder = orderRepository.save(order);
        log.info("Order status updated successfully");

        return orderMapper.toDTO(updatedOrder);
    }

    @Override
    public void deleteOrder(Long orderId) {
        log.info("Deleting order ID: {}", orderId);

        if (!orderRepository.existsById(orderId)) {
            throw new OrderNotFoundException("Order not found with ID: " + orderId);
        }

        orderRepository.deleteById(orderId);
        log.info("Order deleted successfully");
    }
}

