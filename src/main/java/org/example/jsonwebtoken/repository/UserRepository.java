package org.example.jsonwebtoken.repository;

import java.util.Optional;

import org.example.jsonwebtoken.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;



public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByUsername(String username);

    boolean existsByUsername(String username);

    Optional<User> findByGmail(String gmail);
}