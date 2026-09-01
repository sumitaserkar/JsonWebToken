package org.example.jsonwebtoken.service;

import org.example.jsonwebtoken.entity.PasswordResetOtp;
import org.example.jsonwebtoken.entity.User;
import org.example.jsonwebtoken.repository.PasswordResetOtpRepository;
import org.example.jsonwebtoken.repository.UserRepository;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

@Service
public class PasswordResetService {

    private final UserRepository userRepository;
    private final PasswordResetOtpRepository passwordResetOtpRepository;
    private final JavaMailSender javaMailSender;
    private final PasswordEncoder passwordEncoder;

    public PasswordResetService(UserRepository userRepository, PasswordResetOtpRepository passwordResetOtpRepository, JavaMailSender javaMailSender, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordResetOtpRepository = passwordResetOtpRepository;
        this.javaMailSender = javaMailSender;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendOtp (String gmail) {

        // Check user exists
        User user = userRepository.findByEmail(gmail)
                .orElseThrow(() -> new RuntimeException("User not found with this email "));

        // Generate 6 digit OTP
        String otp = String.valueOf(100000 + new Random().nextInt(999999));

        // OTP valid for 5 minutes
        LocalDateTime expiryTime = LocalDateTime.now().plusMinutes(5);

        // Check if OTP already exists for this Gmail
        Optional<PasswordResetOtp> existingOtp =
                passwordResetOtpRepository.findByGmail(gmail);

        if (existingOtp.isPresent()) {

            PasswordResetOtp resetOtp = existingOtp.get();

            resetOtp.setOtp(otp);
            resetOtp.setExpiryTime(expiryTime);

            passwordResetOtpRepository.save(resetOtp);

        } else {

            PasswordResetOtp resetOtp =
                    new PasswordResetOtp(gmail, otp, expiryTime);

            passwordResetOtpRepository.save(resetOtp);
        }

        SimpleMailMessage message = new SimpleMailMessage();

        message.setTo(gmail);
        message.setSubject("ShopGo - Password Reset OTP");
        message.setText(
                "Your ShopGo password reset OTP is: " + otp
                        + "\n\nThis OTP is valid for 5 minutes."
        );

        javaMailSender.send(message);
    }

    public void verifyOpt (String gmail, String otp) {
        PasswordResetOtp resetOtp = passwordResetOtpRepository
                .findByGmail(gmail)
                .orElseThrow(() -> new RuntimeException("OTP not found"));

        // Check OTP
        if (!resetOtp.getOtp().equals(otp)) {
            throw new RuntimeException("Invalid OTP");
        }

        // Check expiry
        if (resetOtp.getExpiryTime().isBefore(LocalDateTime.now())) {
            throw new RuntimeException("OTP has expired");
        }

    }

    public void resetPassword (String gmail, String newPassword, String confirmPassword) {

        if (!newPassword.equals(confirmPassword)) {
            throw new RuntimeException("Passwords do not match");
        }

        User user = userRepository.findByEmail(gmail)
                .orElseThrow(() -> new RuntimeException("User not found"));

        user.setPassword(passwordEncoder.encode(newPassword));

        userRepository.save(user);

        passwordResetOtpRepository.deleteByGmail(gmail);

    }
}
