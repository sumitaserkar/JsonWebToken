package org.example.jsonwebtoken.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.math.BigDecimal;

public class ProductRequestDto {
    private Long id;
    private String name;
    private BigDecimal price;
    private String description;

    @JsonAlias({"photoUrl", "photourl"})
    private String photourl;

    @JsonAlias({"stock", "quantity"})
    private Integer stock;

    public ProductRequestDto() {

    }

    public ProductRequestDto(Long id, String name, BigDecimal price, String description, String photourl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.photourl = photourl;
    }

    public ProductRequestDto(Long id, String name, BigDecimal price, String description, String photourl, Integer stock) {
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
}
