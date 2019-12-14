package com.example.fakebukproject.domain.models.bindings;

public class PicturePostBindingModel {

    private String uploader;

    private String description;

    private String imageUrl;

    public PicturePostBindingModel() {
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
