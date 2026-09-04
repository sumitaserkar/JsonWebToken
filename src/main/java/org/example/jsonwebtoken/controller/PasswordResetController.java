package org.example.jsonwebtoken.controller;

import org.example.jsonwebtoken.dto.ForgotPasswordRequest;
import org.example.jsonwebtoken.dto.ResetPasswordRequest;
import org.example.jsonwebtoken.dto.VerifyOtpRequest;
import org.example.jsonwebtoken.service.PasswordResetService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class PasswordResetController {

    private final PasswordResetService passwordResetService;

    public PasswordResetController(PasswordResetService passwordResetService) {
        this.passwordResetService = passwordResetService;
    }

    @PostMapping("/forgot-password")
    public ResponseEntity<String> forgotPassword(
            @RequestBody ForgotPasswordRequest request) {
        try {
            passwordResetService.sendOtp(request.gmail());
            return ResponseEntity.ok("OTP sent successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {
        try {
            passwordResetService.verifyOpt(
                    request.gmail(),
                    request.otp()
            );
            return ResponseEntity.ok("OTP verified successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword(
            @RequestBody ResetPasswordRequest request) {
        try {
            passwordResetService.resetPassword(
                    request.gmail(),
                    request.newPassword(),
                    request.confirmPassword()
            );
            return ResponseEntity.ok("Password reset successfully");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}