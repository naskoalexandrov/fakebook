package com.example.fakebukproject.domain.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Item extends BaseEntity {

    private String itemName;

    private double price;

    private String description;

    private String itemPictureURL;

    public Item() {
    }

    @Column(nullable = false)
    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    @Column(nullable = false)
    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    @Column(nullable = false)
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Column(nullable = false)
    public String getItemPictureURL() {
        return itemPictureURL;
    }

    public void setItemPictureURL(String itemPictureURL) {
        this.itemPictureURL = itemPictureURL;
    }
}
