package org.example.jsonwebtoken.repository;

import org.example.jsonwebtoken.entity.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Long> {

    Optional<PasswordResetOtp> findByGmail(String gmail);
    void deleteByGmail(String gmail);
}
