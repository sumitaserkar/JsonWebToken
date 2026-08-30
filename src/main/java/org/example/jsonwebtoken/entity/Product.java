package org.example.jsonwebtoken.entity;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "products")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    Long id;
    String name;
    BigDecimal price;
    String description;
    String photourl;
    Integer stock;

    public Product() {
    }

    public Product(Long id, String name, BigDecimal price, String description, String photourl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.photourl = photourl;
    }

    public Product(Long id, String name, BigDecimal price, String description, String photourl, Integer stock) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.photourl = photourl;
        this.stock = stock;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getPhotoUrl() {
        return photourl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photourl = photoUrl;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    // Alias methods for compatibility
    public Integer getQuantity() {
        return stock;
    }

    public void setQuantity(Integer quantity) {
        this.stock = quantity;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", price=" + price +
                ", description='" + description + '\'' +
                ", photourl='" + photourl + '\'' +
                ", stock=" + stock +
                '}';
    }
}
