package com.example.fakebukproject.domain.models.bindings;

public class PostBindingModel {

    private String author;

    private String text;

    public PostBindingModel() {
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
