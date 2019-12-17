package com.example.fakebukproject.error;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Picture not found!")
public class PictureNotFoundException extends RuntimeException {

    private int status;

    public PictureNotFoundException() {
        this.status = 404;
    }

    public PictureNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
