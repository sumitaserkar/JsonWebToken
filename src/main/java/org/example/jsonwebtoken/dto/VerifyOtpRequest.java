package org.example.jsonwebtoken.dto;

public record VerifyOtpRequest(
        String gmail,
        String otp
) {}
