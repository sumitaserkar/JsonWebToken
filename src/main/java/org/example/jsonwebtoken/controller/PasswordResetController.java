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

        passwordResetService.sendOtp(request.gmail());

        return ResponseEntity.ok("OTP sent successfully");
    }

    @PostMapping("/verify-otp")
    public ResponseEntity<String> verifyOtp(@RequestBody VerifyOtpRequest request) {

        passwordResetService.verifyOpt(
                request.gmail(),
                request.otp()
        );

        return ResponseEntity.ok("OTP verified successfully");

    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> resetPassword( @RequestBody ResetPasswordRequest request) {

        passwordResetService.resetPassword(
                request.gmail(),
                request.newPassword(),
                request.confirmPassword()
        );

        return ResponseEntity.ok("Password reset successfully");
    }

}