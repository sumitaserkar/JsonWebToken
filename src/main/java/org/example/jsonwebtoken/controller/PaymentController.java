package org.example.jsonwebtoken.controller;

import java.util.Map;

import jakarta.validation.Valid;

import org.example.jsonwebtoken.dto.CreatePaymentOrderResponse;
import org.example.jsonwebtoken.dto.VerifyPaymentRequest;
import org.example.jsonwebtoken.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/customer/payment")
public class PaymentController {

    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/create-order")
    public ResponseEntity<CreatePaymentOrderResponse> createOrder() {
        return ResponseEntity.ok(paymentService.createPaymentOrder());
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, String>> verifyPayment(@Valid @RequestBody VerifyPaymentRequest request) {
        String message = paymentService.verifyPayment(request);
        return ResponseEntity.ok(Map.of("message", message));
    }
}