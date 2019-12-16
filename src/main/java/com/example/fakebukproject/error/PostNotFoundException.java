package com.example.fakebukproject.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Post not found!")
public class PostNotFoundException extends RuntimeException{

    private int status;

    public PostNotFoundException() {
        this.status = 404;
    }

    public PostNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
