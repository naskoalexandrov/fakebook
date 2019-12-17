package com.example.fakebukproject.domain.models.service;

public class ItemServiceModel extends BaseServiceModel {

    private String seller;

    private String itemName;

    private Double price;

    private String description;

    private String itemPictureURL;

    public ItemServiceModel() {
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

    public String getItemPictureURL() {
        return itemPictureURL;
    }

    public void setItemPictureURL(String itemPictureURL) {
        this.itemPictureURL = itemPictureURL;
    }
}
