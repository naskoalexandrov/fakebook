package com.example.fakebukproject.validation.implementations;

import com.example.fakebukproject.domain.models.service.UserServiceModel;
import com.example.fakebukproject.validation.UserValidationService;

public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isPictureValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }
}
