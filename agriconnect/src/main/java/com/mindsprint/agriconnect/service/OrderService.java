package com.mindsprint.agriconnect.service;

import com.mindsprint.agriconnect.dto.OrderDTO;
import com.mindsprint.agriconnect.entity.OrderStatus;
import java.time.LocalDateTime;
import java.util.List;

public interface OrderService {

    OrderDTO createOrder(OrderDTO orderDTO, String username);

    OrderDTO updateOrder(Long orderId, OrderDTO orderDTO, String username);

    OrderDTO getOrderById(Long orderId);

    OrderDTO getOrderByOrderNumber(String orderNumber);

    List<OrderDTO> getAllOrders();

    List<OrderDTO> getOrdersByUserId(String userId);

    List<OrderDTO> getOrdersByBuyerId(String buyerId);

    List<OrderDTO> getOrdersBySellerId(String sellerId);

    List<OrderDTO> getOrdersByStatus(OrderStatus status);

    List<OrderDTO> getOrdersByDateRange(LocalDateTime startDate, LocalDateTime endDate);

    OrderDTO updateOrderStatus(Long orderId, OrderStatus newStatus, String username);

    void deleteOrder(Long orderId);
}

