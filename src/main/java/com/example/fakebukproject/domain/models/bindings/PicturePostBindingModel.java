package com.example.fakebukproject.domain.models.bindings;

import org.springframework.web.multipart.MultipartFile;

public class PicturePostBindingModel {

    private String uploader;

    private String description;

    private MultipartFile imageUrl;

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

    public MultipartFile getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(MultipartFile imageUrl) {
        this.imageUrl = imageUrl;
    }
}
