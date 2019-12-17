package com.example.fakebukproject.repository;

import com.example.fakebukproject.domain.entities.Picture;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PictureRepository extends JpaRepository<Picture, String> {

    Optional<Picture> findPictureByUploader(String uploader);

}
