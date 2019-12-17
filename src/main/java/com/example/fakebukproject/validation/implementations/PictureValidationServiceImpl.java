package com.example.fakebukproject.validation.implementations;

import com.example.fakebukproject.domain.models.service.PictureServiceModel;
import com.example.fakebukproject.validation.PictureValidationService;

public class PictureValidationServiceImpl implements PictureValidationService {
    @Override
    public boolean isPictureValid(PictureServiceModel pictureServiceModel) {
        return pictureServiceModel != null;
    }
}
