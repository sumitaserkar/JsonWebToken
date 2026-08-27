package org.example.jsonwebtoken.repository;


import org.example.jsonwebtoken.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository <Product, Long> {
}
