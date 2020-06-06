package com.example.fakebukproject.service;

public interface EmailService {
    void sendMessage(String to, String subject, String text);
}
