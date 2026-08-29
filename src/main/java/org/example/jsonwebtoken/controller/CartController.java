package org.example.jsonwebtoken.controller;

import java.util.Map;

import jakarta.validation.Valid;

import org.example.jsonwebtoken.dto.AddToCartRequest;
import org.example.jsonwebtoken.dto.CartResponse;
import org.example.jsonwebtoken.dto.UpdateCartRequest;
import org.example.jsonwebtoken.service.CartService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/customer/cart")
public class CartController {

    private final CartService cartService;

    public CartController(CartService cartService) {
        this.cartService = cartService;
    }

    @PostMapping
    public ResponseEntity<Map<String, String>> addToCart(@Valid @RequestBody AddToCartRequest request) {
        String message = cartService.addToCart(request);
        return ResponseEntity.ok(Map.of("message", message));
    }

    @GetMapping
    public ResponseEntity<CartResponse> getCart() {
        return ResponseEntity.ok(cartService.getCart());
    }

    @PutMapping("/{cartItemId}")
    public ResponseEntity<Map<String, String>> updateCartItem(@PathVariable Long cartItemId,
                                                              @Valid @RequestBody UpdateCartRequest request) {
        String message = cartService.updateCartItem(cartItemId, request);
        return ResponseEntity.ok(Map.of("message", message));
    }

    @DeleteMapping("/{cartItemId}")
    public ResponseEntity<Map<String, String>> removeCartItem(@PathVariable Long cartItemId) {
        String message = cartService.removeCartItem(cartItemId);
        return ResponseEntity.ok(Map.of("message", message));
    }
}