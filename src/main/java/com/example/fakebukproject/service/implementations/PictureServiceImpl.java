package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Picture;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.domain.models.service.PictureServiceModel;
import com.example.fakebukproject.error.Constants;
import com.example.fakebukproject.error.PictureNotFoundException;
import com.example.fakebukproject.repository.PictureRepository;
import com.example.fakebukproject.service.LogService;
import com.example.fakebukproject.service.PictureService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<PictureServiceModel> findPictureByDescription(String description) {
        return this.pictureRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PictureServiceModel.class))
                .filter(p -> p.getDescription().equals(description))
                .collect(Collectors.toList());
    }

    @Override
    public List<PictureServiceModel> findPictureByUploader(String uploader) {
        return this.pictureRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PictureServiceModel.class))
                .filter(p -> p.getUploader().equals(uploader))
                .collect(Collectors.toList());
    }

    @Override
    public List<PictureServiceModel> findAllPictures() {
        return this.pictureRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PictureServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PictureServiceModel findPictureById(String id) {
        return this.pictureRepository.findById(id)
                .map(p -> this.modelMapper.map(p, PictureServiceModel.class))
                .orElseThrow(() -> new PictureNotFoundException(Constants.PICTURE_ID_NOT_FOUND));
    }

    @Override
    public void deletePicture(String id) {
        Picture picture = this.pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException(Constants.PICTURE_ID_NOT_FOUND));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(picture.getUploader());
        logServiceModel.setDescription("Picture deleted");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        this.pictureRepository.delete(picture);

    }

    @Override
    public PictureServiceModel editPicture(String id, PictureServiceModel pictureServiceModel) {
        Picture picture = this.pictureRepository.findById(id)
                .orElseThrow(() -> new PictureNotFoundException(Constants.PICTURE_ID_NOT_FOUND));

        picture.setUploader(pictureServiceModel.getUploader());
        picture.setDescription(pictureServiceModel.getDescription());
        picture.setImageUrl(pictureServiceModel.getImageUrl());

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(pictureServiceModel.getUploader());
        logServiceModel.setDescription("Picture edited");
        logServiceModel.setTime(LocalDateTime.now());

        return this.modelMapper.map(this.pictureRepository.saveAndFlush(picture), PictureServiceModel.class);
    }
}
