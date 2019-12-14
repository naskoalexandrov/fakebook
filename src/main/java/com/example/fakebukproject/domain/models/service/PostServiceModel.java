package com.example.fakebukproject.domain.models.service;

public class PostServiceModel {

    private String author;

    private String text;

    public PostServiceModel() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
