package com.example.fakebukproject.service;

import com.example.fakebukproject.domain.models.service.LogServiceModel;

public interface LogService {

    LogServiceModel putLogInDatabase(LogServiceModel logServiceModel);
}
