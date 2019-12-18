package com.example.fakebukproject.web.controllers;

import com.example.fakebukproject.domain.models.bindings.PicturePostBindingModel;
import com.example.fakebukproject.domain.models.bindings.PostBindingModel;
import com.example.fakebukproject.domain.models.service.PictureServiceModel;
import com.example.fakebukproject.domain.models.service.PostServiceModel;
import com.example.fakebukproject.domain.models.view.PictureViewModel;
import com.example.fakebukproject.domain.models.view.UserProfileViewModel;
import com.example.fakebukproject.error.PictureNotFoundException;
import com.example.fakebukproject.service.PictureService;
import com.example.fakebukproject.service.UserService;
import com.example.fakebukproject.web.annotations.PageTitle;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.StringTrimmerEditor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

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

    @GetMapping("/all-by-user")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Pictures By User")
    public ModelAndView showAllByUser(Principal principal, ModelAndView modelAndView){

        modelAndView
                .addObject("model", this.modelMapper.
                        map(this.pictureService.findPictureByUploader(principal.getName()), PictureViewModel.class));

        List<PictureServiceModel> pictures = this.pictureService.findAllPictures()
                .stream()
                .filter(p -> p.getUploader().equals(principal.getName()))
                .map(p -> this.modelMapper.map(p, PictureServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("pictures", pictures);
        return super.view("picture/all-pictures-by-user", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Pictures")
    public ModelAndView showAll(Principal principal, ModelAndView modelAndView){

        modelAndView
                .addObject("model", this.modelMapper.
                        map(this.pictureService.findPictureByUploader(principal.getName()), PictureViewModel.class));

        List<PictureServiceModel> pictures = this.pictureService.findAllPictures()
                .stream()
                .map(p -> this.modelMapper.map(p, PictureServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("pictures", pictures);
        return super.view("picture/all-pictures", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Edit Picture")
    public ModelAndView editPicture(@PathVariable String id, ModelAndView modelAndView){
        PictureServiceModel pictureServiceModel = this.pictureService.findPictureById(id);

        modelAndView.addObject("picture", pictureServiceModel);
        modelAndView.addObject("pictureId", id);

        return super.view("picture/edit-picture",modelAndView);

    }

    @PostMapping("/edit/{id}")
    public ModelAndView editPictureConfirm(@PathVariable String id,@ModelAttribute PicturePostBindingModel model){
        this.pictureService.editPicture(id, this.modelMapper.map(model, PictureServiceModel.class));

        return super.redirect("/pictures/all-by-user");
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deletePicture(@PathVariable String id, ModelAndView modelAndView) {
        PictureServiceModel pictureServiceModel = this.pictureService.findPictureById(id);

        modelAndView.addObject("picture", pictureServiceModel);
        modelAndView.addObject("pictureId", id);

        return super.view("picture/delete-picture", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PageTitle("Delete Picture")
    public ModelAndView deletePictureConfirm(@PathVariable String id) {
        this.pictureService.deletePicture(id);

        return super.redirect("/pictures/all-by-user");
    }

    @GetMapping("/all-pictures")
    @ResponseBody
    public List<PictureViewModel> fetchAllPictures() {
        return this.pictureService.findAllPictures()
                .stream()
                .map(p -> this.modelMapper.map(p, PictureViewModel.class))
                .collect(Collectors.toList());
    }

    @ExceptionHandler({PictureNotFoundException.class})
    public ModelAndView handleVideoNotFound(PictureNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatus());

        return modelAndView;
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }
}
