package com.mindsprint.agriconnect.controller;

import com.mindsprint.agriconnect.dto.OrderDTO;
import com.mindsprint.agriconnect.entity.OrderStatus;
import com.mindsprint.agriconnect.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
@Slf4j
//@SecurityRequirement(name = "bearer-jwt")
public class OrderController {

    private final OrderService orderService;


    @PreAuthorize("hasAnyRole('USER', 'BUYER', 'SELLER', 'ADMIN')")
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @RequestBody OrderDTO orderDTO,
            Authentication authentication) {
        log.info("Creating order: {} by user: {}", orderDTO.getOrderNumber(), authentication.getName());
        OrderDTO createdOrder = orderService.createOrder(orderDTO, authentication.getName());
        return new ResponseEntity<>(createdOrder, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getAllOrders() {
        log.info("Fetching all orders");
        List<OrderDTO> orders = orderService.getAllOrders();
        return ResponseEntity.ok(orders);
    }


    @PreAuthorize("hasAnyRole('USER', 'BUYER', 'SELLER', 'ADMIN')")
    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long orderId) {
        log.info("Fetching order by ID: {}", orderId);
        OrderDTO order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }


    @PreAuthorize("hasAnyRole('USER', 'BUYER', 'SELLER', 'ADMIN')")
    @GetMapping("/number/{orderNumber}")
    public ResponseEntity<OrderDTO> getOrderByOrderNumber(@PathVariable String orderNumber) {
        log.info("Fetching order by number: {}", orderNumber);
        OrderDTO order = orderService.getOrderByOrderNumber(orderNumber);
        return ResponseEntity.ok(order);
    }


    @PreAuthorize("hasAnyRole('USER', 'BUYER', 'SELLER', 'ADMIN')")
    @GetMapping("/user")
    public ResponseEntity<List<OrderDTO>> getOrdersByUser(Authentication authentication) {
        log.info("Fetching orders for user: {}", authentication.getName());
        List<OrderDTO> orders = orderService.getOrdersByUserId(authentication.getName());
        return ResponseEntity.ok(orders);
    }


    @PreAuthorize("hasAnyRole('BUYER', 'ADMIN')")
    @GetMapping("/buyer/{buyerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersByBuyerId(@PathVariable String buyerId) {
        log.info("Fetching orders for buyer: {}", buyerId);
        List<OrderDTO> orders = orderService.getOrdersByBuyerId(buyerId);
        return ResponseEntity.ok(orders);
    }


    @PreAuthorize("hasAnyRole('SELLER', 'ADMIN')")
    @GetMapping("/seller/{sellerId}")
    public ResponseEntity<List<OrderDTO>> getOrdersBySellerId(@PathVariable String sellerId) {
        log.info("Fetching orders for seller: {}", sellerId);
        List<OrderDTO> orders = orderService.getOrdersBySellerId(sellerId);
        return ResponseEntity.ok(orders);
    }

    @PreAuthorize("hasAnyRole('USER', 'ADMIN')")
    @GetMapping("/status/{status}")
    public ResponseEntity<List<OrderDTO>> getOrdersByStatus(@PathVariable OrderStatus status) {
        log.info("Fetching orders with status: {}", status);
        List<OrderDTO> orders = orderService.getOrdersByStatus(status);
        return ResponseEntity.ok(orders);
    }


    @PreAuthorize("hasAnyRole('ADMIN')")
    @GetMapping("/date-range")
    public ResponseEntity<List<OrderDTO>> getOrdersByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate) {
        log.info("Fetching orders between {} and {}", startDate, endDate);
        List<OrderDTO> orders = orderService.getOrdersByDateRange(startDate, endDate);
        return ResponseEntity.ok(orders);
    }


    @PreAuthorize("hasAnyRole('USER', 'BUYER', 'SELLER', 'ADMIN')")
    @PutMapping("/{orderId}")
    public ResponseEntity<OrderDTO> updateOrder(
            @PathVariable Long orderId,
            @RequestBody OrderDTO orderDTO,
            Authentication authentication) {
        log.info("Updating order ID: {} by user: {}", orderId, authentication.getName());
        OrderDTO updatedOrder = orderService.updateOrder(orderId, orderDTO, authentication.getName());
        return ResponseEntity.ok(updatedOrder);
    }



    @PatchMapping("/{orderId}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @PathVariable Long orderId,
            @RequestParam OrderStatus status,
            Authentication authentication) {
        log.info("Updating status for order ID: {} to {} by user: {}", orderId, status, authentication.getName());
        OrderDTO updatedOrder = orderService.updateOrderStatus(orderId, status, authentication.getName());
        return ResponseEntity.ok(updatedOrder);
    }


    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping("/{orderId}")
    public ResponseEntity<Void> deleteOrder(@PathVariable Long orderId) {
        log.info("Deleting order ID: {}", orderId);
        orderService.deleteOrder(orderId);
        return ResponseEntity.noContent().build();
    }
}
