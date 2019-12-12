package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Log;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.repository.LogRepository;
import com.example.fakebukproject.service.LogService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LogServiceImpl implements LogService {

    private final LogRepository logRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public LogServiceImpl(LogRepository logRepository, ModelMapper modelMapper) {
        this.logRepository = logRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public LogServiceModel putLogInDatabase(LogServiceModel logServiceModel) {
        Log log = this.modelMapper.map(logServiceModel, Log.class);
        return this.modelMapper.map(this.logRepository.saveAndFlush(log), LogServiceModel.class);
    }
}
