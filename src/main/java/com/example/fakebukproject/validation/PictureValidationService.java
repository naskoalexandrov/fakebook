package com.example.fakebukproject.validation;

import com.example.fakebukproject.domain.models.service.PictureServiceModel;

public interface PictureValidationService {

    boolean isPictureValid(PictureServiceModel pictureServiceModel);
}
