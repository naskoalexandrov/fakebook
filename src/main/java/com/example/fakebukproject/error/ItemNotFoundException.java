package com.example.fakebukproject.error;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.NOT_FOUND, reason = "Item not found!")
public class ItemNotFoundException extends RuntimeException{

    private int status;

    public ItemNotFoundException() {
        this.status = 404;
    }

    public ItemNotFoundException(String message) {
        super(message);
        this.status = 404;
    }

    public int getStatus() {
        return status;
    }
}
