package org.example.jsonwebtoken.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.time.LocalDate;
public record RegisterRequest(
        @NotBlank String name,
        @NotBlank String username,
        @NotBlank @Size(min = 6) String password,
        @NotBlank String email,
        @NotBlank String mobile,
        @NotBlank String gender,
        LocalDate dob

) {
}