package com.example.fakebukproject.web.controllers;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

public class GlobalExceptionHandler extends BaseController {

    @ExceptionHandler({Throwable.class})
    public ModelAndView handleSqlException(Throwable e){
        ModelAndView modelAndView = new ModelAndView("error");

        Throwable throwable = e;

        while (throwable.getCause() != null){
            throwable = throwable.getCause();
        }

        modelAndView.addObject("message",throwable.getMessage());

        return modelAndView;
    }
}
