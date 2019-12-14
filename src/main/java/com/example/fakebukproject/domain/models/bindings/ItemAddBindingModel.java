package com.example.fakebukproject.domain.models.bindings;

public class ItemAddBindingModel {

    private String itemName;

    private double price;

    private String description;

    private String itemPictureURL;

    public ItemAddBindingModel() {
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
