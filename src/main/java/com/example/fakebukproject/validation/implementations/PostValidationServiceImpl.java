package com.example.fakebukproject.validation.implementations;

import com.example.fakebukproject.domain.models.service.PostServiceModel;
import com.example.fakebukproject.validation.PostValidationService;

public class PostValidationServiceImpl implements PostValidationService {
    @Override
    public boolean isPostValid(PostServiceModel pictureServiceModel) {
        return pictureServiceModel != null;
    }
}
