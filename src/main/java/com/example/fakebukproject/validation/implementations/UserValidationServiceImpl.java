package com.example.fakebukproject.validation.implementations;

import com.example.fakebukproject.domain.models.service.UserServiceModel;
import com.example.fakebukproject.validation.UserValidationService;

public class UserValidationServiceImpl implements UserValidationService {
    @Override
    public boolean isUserValid(UserServiceModel userServiceModel) {
        return userServiceModel != null;
    }
}
