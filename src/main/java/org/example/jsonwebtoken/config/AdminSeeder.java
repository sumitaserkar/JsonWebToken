package org.example.jsonwebtoken.config;

import org.example.jsonwebtoken.entity.User;
import org.example.jsonwebtoken.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;

@Configuration
public class AdminSeeder {

    @Bean
    public CommandLineRunner seedAdmin(UserRepository userRepository,
                                       PasswordEncoder passwordEncoder) {
        return args -> {

            String adminUsername = "admin";

            if (!userRepository.existsByUsername(adminUsername)) {

                User admin = new User(
                        "Admin",
                        adminUsername,
                        passwordEncoder.encode("admin123"),
                        "ADMIN",
                        "admin@gmail.com",
                        "9999999999",
                        "Male",
                        LocalDate.of(2000, 1, 1)
                );

                userRepository.save(admin);

                System.out.println("Admin user created: admin / admin123");
            }
        };
    }
}