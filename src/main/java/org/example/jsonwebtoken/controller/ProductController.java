package org.example.jsonwebtoken.controller;

import org.example.jsonwebtoken.dto.ProductRequestDto;
import org.example.jsonwebtoken.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class    ProductController {

    ProductService productService;

    public ProductController (ProductService productService) {
        this.productService  = productService;
    }

    @PostMapping("/addProduct")
    public String addProduct(@RequestBody ProductRequestDto product) {
        return productService.addProduct(product);
    }

    @PatchMapping("/updateProduct")
    public String updateProduct(@RequestBody ProductRequestDto product) {
        return productService.updateProduct(product);
    }

    @DeleteMapping("/deleteProduct/{productId}")
    public String deleteProduct(@PathVariable Long productId) {
        return productService.deleteProduct(productId);
    }

    @GetMapping("/viewProduct/{productId}")
    public ProductRequestDto viewProduct(@PathVariable Long productId) {
        return productService.viewProduct(productId);
    }

    @GetMapping("/viewAllProducts")
    public List<ProductRequestDto> viewAllProducts() {
        return productService.viewAllProducts();
    }
}
