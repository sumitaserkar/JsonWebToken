package org.example.jsonwebtoken.service;


import org.example.jsonwebtoken.dto.ProductRequestDto;

import java.util.List;

public interface ProductService {

    String addProduct(ProductRequestDto product);
    String updateProduct(ProductRequestDto product);
    String deleteProduct(Long ProductId);
    ProductRequestDto viewProduct(Long ProductId);
    List<ProductRequestDto> viewAllProducts();
}
