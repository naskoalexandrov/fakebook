package com.example.fakebukproject.web.controllers;

import com.example.fakebukproject.domain.models.bindings.PostBindingModel;
import com.example.fakebukproject.domain.models.service.PostServiceModel;
import com.example.fakebukproject.domain.models.view.UserProfileViewModel;
import com.example.fakebukproject.service.PostService;
import com.example.fakebukproject.service.UserService;
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
@RequestMapping("/posts")
public class PostController extends BaseController {

    private final PostService postService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public PostController(PostService postService, UserService userService, ModelMapper modelMapper) {
        this.postService = postService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/post")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView post(Principal principal, ModelAndView modelAndView){
        modelAndView.addObject("model", this.modelMapper
                .map(this.userService.findUserByUserName(principal.getName()), UserProfileViewModel.class));

        return super.view("posting/post");
    }

    @PostMapping("/post")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView postConfirm (@ModelAttribute PostBindingModel model){

        this.postService.posting(this.modelMapper.map(model, PostServiceModel.class));

        return super.redirect("/home");
    }

}
