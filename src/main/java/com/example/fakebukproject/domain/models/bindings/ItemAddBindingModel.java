package com.example.fakebukproject.domain.models.bindings;

import org.springframework.web.multipart.MultipartFile;

public class ItemAddBindingModel {

    private String seller;

    private String itemName;

    private Double price;

    private String description;

    private MultipartFile itemPictureURL;

    public ItemAddBindingModel() {
    }

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public MultipartFile getItemPictureURL() {
        return itemPictureURL;
    }

    public void setItemPictureURL(MultipartFile itemPictureURL) {
        this.itemPictureURL = itemPictureURL;
    }
}
