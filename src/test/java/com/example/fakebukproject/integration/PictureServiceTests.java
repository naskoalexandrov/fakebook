package com.example.fakebukproject.integration;

import com.example.fakebukproject.repository.PictureRepository;
import com.example.fakebukproject.service.PictureService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PictureServiceTests {

    @Autowired
    private PictureService service;

    @MockBean
    private PictureRepository mockPictureRepository;

    @Test(expected = IllegalArgumentException.class)
    public void postPictureWithInvalidValues_ThrowError() {
        service.postPicture(null);
        verify(mockPictureRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void pictureService_findPictureByUploaderWithInvalidValue_ThrowError() {
        service.findPictureByUploader(null);
        verify(mockPictureRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void pictureService_findPictureByDescriptionWithInvalidValue_ThrowError() {
        service.findPictureByDescription(null);
        verify(mockPictureRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void pictureService_findAllPicturesWithInvalidValue_ThrowError() {
        service.findAllPictures();
        verify(mockPictureRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void pictureService_findPictureByIdWithInvalidValue_ThrowError() {
        service.findPictureById(null);
        verify(mockPictureRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void pictureService_deletePictureWithInvalidValue_ThrowError() {
        service.deletePicture(null);
        verify(mockPictureRepository)
                .save(any());
    }

    @Test(expected = Exception.class)
    public void pictureService_editPictureWithInvalidValue_ThrowError() {
        service.editPicture(null, null);
        verify(mockPictureRepository)
                .save(any());
    }

}
