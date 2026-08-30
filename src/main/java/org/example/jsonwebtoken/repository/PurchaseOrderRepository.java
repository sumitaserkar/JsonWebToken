package org.example.jsonwebtoken.repository;

import java.util.Optional;

import org.example.jsonwebtoken.entity.PurchaseOrder;
import org.example.jsonwebtoken.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PurchaseOrderRepository extends JpaRepository<PurchaseOrder, Long> {
    Optional<PurchaseOrder> findByIdAndUser(Long id, User user);
}