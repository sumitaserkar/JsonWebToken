package org.example.jsonwebtoken.entity;
import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "password_reset_otp")
public class PasswordResetOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String gmail;

    private String otp;

    private LocalDateTime expiryTime;

    public PasswordResetOtp() {
    }

    public PasswordResetOtp( String gmail, String otp, LocalDateTime expiryTime) {
        this.gmail = gmail;
        this.otp = otp;
        this.expiryTime = expiryTime;
    }

    public Long getId() {
        return id;
    }

    public String getGmail() {
        return gmail;
    }

    public void setGmail(String gmail) {
        this.gmail = gmail;
    }

    public String getOtp() {
        return otp;
    }

    public void setOtp(String otp) {
        this.otp = otp;
    }

    public LocalDateTime getExpiryTime() {
        return expiryTime;
    }

    public void setExpiryTime(LocalDateTime expiryTime) {
        this.expiryTime = expiryTime;
    }
}