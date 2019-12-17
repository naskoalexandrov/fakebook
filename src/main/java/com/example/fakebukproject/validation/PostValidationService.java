package com.example.fakebukproject.validation;

import com.example.fakebukproject.domain.models.service.PostServiceModel;

public interface PostValidationService {
    boolean isPostValid(PostServiceModel pictureServiceModel);
}
