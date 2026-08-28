package org.example.jsonwebtoken.dto;

import com.fasterxml.jackson.annotation.JsonAlias;

public class ProductRequestDto {
    private Long id;
    private String name;
    private int price;
    private String description;

    @JsonAlias({"photoUrl", "photourl"})
    private String photourl;

    public ProductRequestDto() {

    }

    public ProductRequestDto(Long id, String name, int price, String description, String photourl) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.description = description;
        this.photourl = photourl;
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

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
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
}
