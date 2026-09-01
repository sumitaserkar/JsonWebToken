package org.example.jsonwebtoken.dto;

public record ResetPasswordRequest(
        String gmail,
        String newPassword,
        String confirmPassword
) {}
