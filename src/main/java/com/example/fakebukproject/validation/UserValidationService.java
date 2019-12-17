package com.example.fakebukproject.validation;

import com.example.fakebukproject.domain.models.service.UserServiceModel;

public interface UserValidationService {

    boolean isPictureValid(UserServiceModel userServiceModel);
}
