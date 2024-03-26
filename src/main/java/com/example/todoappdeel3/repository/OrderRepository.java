package com.example.todoappdeel3.repository;

import com.example.todoappdeel3.Enums.OrderStatus;
import com.example.todoappdeel3.dto.OrderDTO;
import com.example.todoappdeel3.dto.PlaceOrderDTO;
import com.example.todoappdeel3.models.Category;
import com.example.todoappdeel3.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Optional<Order> findByUserIdAndOrderStatus(Long userid, OrderStatus orderStatus);

    Optional<Order> findByUserIdAndOrderStatusAndId(Long userid, OrderStatus orderStatus, Long orderId);

    Optional<List<Order>> findOrdersByUserIdAndOrderStatus(Long userid, OrderStatus orderStatus);



    Optional<Order> findOrderById(Long orderId);

//   Optional<OrderDTO> minusQuantity(Long userid, Long productId);

//   ResponseEntity<String> addQuantity(Long userid, Long productId);
}