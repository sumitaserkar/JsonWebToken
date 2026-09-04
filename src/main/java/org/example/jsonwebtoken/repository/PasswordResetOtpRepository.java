package org.example.jsonwebtoken.repository;

import org.example.jsonwebtoken.entity.PasswordResetOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

public interface PasswordResetOtpRepository extends JpaRepository<PasswordResetOtp, Long> {

    Optional<PasswordResetOtp> findByGmail(String gmail);

    @Modifying
    @Transactional
    void deleteByGmail(String gmail);
}
