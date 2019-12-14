package com.example.fakebukproject.web.controllers;

import com.example.fakebukproject.domain.models.bindings.PicturePostBindingModel;
import com.example.fakebukproject.domain.models.service.PictureServiceModel;
import com.example.fakebukproject.domain.models.view.UserProfileViewModel;
import com.example.fakebukproject.service.PictureService;
import com.example.fakebukproject.service.UserService;
import com.example.fakebukproject.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;

@Controller
@RequestMapping("/pictures")
public class PictureController extends BaseController{

    private final PictureService pictureService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public PictureController(PictureService pictureService, UserService userService, ModelMapper modelMapper) {
        this.pictureService = pictureService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/post")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Post Picture")
    public ModelAndView postPicture (Principal principal, ModelAndView modelAndView){
        modelAndView.addObject("model", this.modelMapper
                .map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));
        return super.view("picture/post-picture");
    }

    @PostMapping("/post")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView postPirctureConfirm (@ModelAttribute PicturePostBindingModel model){

        this.pictureService.postPicture(this.modelMapper.map(model, PictureServiceModel.class));

        return super.redirect("/home");
    }
}
