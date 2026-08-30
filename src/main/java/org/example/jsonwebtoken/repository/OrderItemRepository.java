package org.example.jsonwebtoken.repository;

import org.example.jsonwebtoken.entity.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;


public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}