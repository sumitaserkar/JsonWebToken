package org.example.jsonwebtoken.service;
import java.math.BigDecimal;
import java.util.List;

import org.example.jsonwebtoken.dto.AddToCartRequest;
import org.example.jsonwebtoken.dto.CartItemResponse;
import org.example.jsonwebtoken.dto.CartResponse;
import org.example.jsonwebtoken.dto.UpdateCartRequest;
import org.example.jsonwebtoken.entity.CartItem;
import org.example.jsonwebtoken.entity.Product;
import org.example.jsonwebtoken.entity.User;
import org.example.jsonwebtoken.repository.CartItemRepository;
import org.example.jsonwebtoken.repository.ProductRepository;
import org.example.jsonwebtoken.repository.UserRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CartService {

    private final CartItemRepository cartItemRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartItemRepository cartItemRepository,
                       ProductRepository productRepository,
                       UserRepository userRepository) {
        this.cartItemRepository = cartItemRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public String addToCart(AddToCartRequest request) {
        User user = getCurrentUser();

        Product product = productRepository.findById(request.productId())
                .orElseThrow(() -> new RuntimeException("Product not found with id: " + request.productId()));

        int availableStock = product.getStock() != null ? product.getStock() : 0;
        if (availableStock <= 0) {
            throw new RuntimeException("Product is out of stock");
        }

        CartItem cartItem = cartItemRepository.findByUserAndProduct(user, product)
                .orElse(null);

        if (cartItem != null) {
            int newQuantity = cartItem.getQuantity() + request.quantity();

            if (newQuantity > availableStock) {
                throw new RuntimeException("Requested quantity (" + newQuantity + ") exceeds available stock (" + availableStock + ")");
            }

            cartItem.setQuantity(newQuantity);
            cartItemRepository.save(cartItem);
            return "Product quantity updated in cart";
        }

        if (request.quantity() > availableStock) {
            throw new RuntimeException("Requested quantity (" + request.quantity() + ") exceeds available stock (" + availableStock + ")");
        }

        CartItem newCartItem = new CartItem(user, product, request.quantity());
        cartItemRepository.save(newCartItem);

        return "Product added to cart successfully";
    }

    public CartResponse getCart() {
        User user = getCurrentUser();

        List<CartItemResponse> items = cartItemRepository.findByUser(user)
                .stream()
                .map(this::mapToCartItemResponse)
                .toList();

        BigDecimal totalAmount = items.stream()
                .map(CartItemResponse::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        return new CartResponse(items, totalAmount);
    }

    public String updateCartItem(Long cartItemId, UpdateCartRequest request) {
        User user = getCurrentUser();

        CartItem cartItem = cartItemRepository.findByIdAndUser(cartItemId, user)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        int availableStock = cartItem.getProduct().getStock() != null ? cartItem.getProduct().getStock() : 0;
        if (request.quantity() > availableStock) {
            throw new RuntimeException("Requested quantity (" + request.quantity() + ") exceeds available stock (" + availableStock + ")");
        }

        cartItem.setQuantity(request.quantity());
        cartItemRepository.save(cartItem);

        return "Cart item updated successfully";
    }

    public String removeCartItem(Long cartItemId) {
        User user = getCurrentUser();

        CartItem cartItem = cartItemRepository.findByIdAndUser(cartItemId, user)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cartItemRepository.delete(cartItem);
        return "Cart item removed successfully";
    }

    @Transactional
    public String checkout() {
        User user = getCurrentUser();

        List<CartItem> cartItems = cartItemRepository.findByUser(user);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("Your cart is empty");
        }

        // Validate all cart items against stock
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            int available = product.getStock() != null ? product.getStock() : 0;
            if (item.getQuantity() > available) {
                throw new RuntimeException("Insufficient stock for product '" + product.getName() + "'. Available: " + available + ", in cart: " + item.getQuantity());
            }
        }

        // Deduct stock for each product
        for (CartItem item : cartItems) {
            Product product = item.getProduct();
            int currentStock = product.getStock() != null ? product.getStock() : 0;
            product.setStock(currentStock - item.getQuantity());
            productRepository.save(product);
        }

        // Clear cart
        cartItemRepository.deleteAll(cartItems);

        return "Order placed successfully! Product stock has been updated.";
    }

    private User getCurrentUser() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("Logged in user not found"));
    }

    private CartItemResponse mapToCartItemResponse(CartItem cartItem) {
        Product product = cartItem.getProduct();
        BigDecimal subtotal = product.getPrice().multiply(BigDecimal.valueOf(cartItem.getQuantity()));

        return new CartItemResponse(
                cartItem.getId(),
                product.getId(),
                product.getName(),
                product.getPrice(),
                cartItem.getQuantity(),
                subtotal,
                product.getPhotourl()
        );
    }
}