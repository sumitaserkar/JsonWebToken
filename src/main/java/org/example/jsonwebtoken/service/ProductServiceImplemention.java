package org.example.jsonwebtoken.service;


import org.example.jsonwebtoken.dto.ProductRequestDto;
import org.example.jsonwebtoken.entity.Product;
import org.example.jsonwebtoken.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class ProductServiceImplemention implements ProductService {

    ProductRepository productRepository;
    public ProductServiceImplemention (ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public String addProduct(ProductRequestDto product) {
        Product p = new Product();
        p.setId(product.getId());
        p.setName(product.getName());
        p.setPrice(product.getPrice());
        p.setDescription(product.getDescription());
        String photo = product.getPhotourl() != null ? product.getPhotourl() : product.getPhotoUrl();
        p.setPhotourl(photo);
        productRepository.save(p);
        return "Product Added Successfully";
    }

    @Override
    public String updateProduct(ProductRequestDto product) {
        Product p = productRepository.findById(product.getId()).orElse(new Product());
        p.setId(product.getId());
        if (product.getName() != null && !product.getName().isEmpty()) {
            p.setName(product.getName());
        }
        if (product.getPrice() != null) {
            p.setPrice(product.getPrice());
        }
        if (product.getDescription() != null) {
            p.setDescription(product.getDescription());
        }
        String photo = product.getPhotourl() != null ? product.getPhotourl() : product.getPhotoUrl();
        if (photo != null && !photo.isEmpty()) {
            p.setPhotourl(photo);
        }
        productRepository.save(p);
        return "Product Updated Successfully";
    }

    @Override
    public String deleteProduct(Long productId) {
        productRepository.deleteById(productId);
        return "Product deleted Successfully";
    }

    @Override
    public ProductRequestDto viewProduct(Long productId) {
        Product p = productRepository.findById(productId).orElseThrow(() -> new RuntimeException("Product not found"));
        return new  ProductRequestDto (
                p.getId(),
                p.getName(),
                p.getPrice(),
                p.getDescription(),
                p.getPhotourl()
        );
    }

    @Override
    public List<ProductRequestDto> viewAllProducts() {
        List <Product> p = productRepository.findAll();
        return p.stream().map(product -> new ProductRequestDto(
                product.getId(),
                product.getName(),
                product.getPrice(),
                product.getDescription(),
                product.getPhotourl()
        )).toList();
    }
}
