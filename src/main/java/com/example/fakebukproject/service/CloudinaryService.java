package com.example.fakebukproject.service;


import org.springframework.web.multipart.MultipartFile;

import javax.mail.Multipart;
import java.io.IOException;

public interface CloudinaryService {
    String uploadImage(MultipartFile multipartFile) throws IOException;
}
