package com.example.fakebukproject.domain.models.bindings;

public class PictureEditBindingModel {
    private String uploader;

    private String description;

    private String imageUrl;

    public PictureEditBindingModel() {
    }

    public String getUploader() {
        return uploader;
    }

    public void setUploader(String uploader) {
        this.uploader = uploader;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
}
