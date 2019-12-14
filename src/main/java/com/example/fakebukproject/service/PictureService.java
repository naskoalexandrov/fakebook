package com.example.fakebukproject.service;

import com.example.fakebukproject.domain.models.service.PictureServiceModel;

import java.util.List;

public interface PictureService {

    PictureServiceModel postPicture(PictureServiceModel pictureServiceModel);

    PictureServiceModel findPictureByDescription(String description);

    List<PictureServiceModel> findPictureByUploader(String uploader);

    List<PictureServiceModel> findAllPictures();

    PictureServiceModel findPictureById(String id);

    void deletePicture(String id);

    PictureServiceModel editPicture(String id, PictureServiceModel pictureServiceModel);
}
