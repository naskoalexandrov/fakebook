package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Picture;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.domain.models.service.PictureServiceModel;
import com.example.fakebukproject.repository.PictureRepository;
import com.example.fakebukproject.service.LogService;
import com.example.fakebukproject.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PictureServiceImpl implements PictureService {

    private final LogService logService;
    private final PictureRepository pictureRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureServiceImpl(LogService logService, PictureRepository pictureRepository, ModelMapper modelMapper) {
        this.logService = logService;
        this.pictureRepository = pictureRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public PictureServiceModel postPicture(PictureServiceModel pictureServiceModel) {
        Picture picture = this.modelMapper.map(pictureServiceModel, Picture.class);

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(pictureServiceModel.getUploader().toLowerCase());
        logServiceModel.setDescription("Picture created");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        return this.modelMapper.map(this.pictureRepository.saveAndFlush(picture), PictureServiceModel.class);
    }

    @Override
    public PictureServiceModel findPictureByDescription(String description) {
        return null;
    }

    @Override
    public List<PictureServiceModel> findPictureByUploader(String author) {
        return null;
    }

    @Override
    public List<PictureServiceModel> findAllPictures() {
        return null;
    }

    @Override
    public PictureServiceModel findPictureById(String id) {
        return null;
    }

    @Override
    public void deletePicture(String id) {

    }

    @Override
    public PictureServiceModel editPicture(String id, PictureServiceModel pictureServiceModel) {
        return null;
    }
}
