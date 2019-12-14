package com.example.fakebukproject.web.controllers;

import com.example.fakebukproject.domain.models.bindings.ItemAddBindingModel;
import com.example.fakebukproject.domain.models.service.ItemServiceModel;
import com.example.fakebukproject.domain.models.view.UserProfileViewModel;
import com.example.fakebukproject.service.ItemService;
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
@RequestMapping("/items")
public class ItemController extends BaseController {

    private final ItemService itemService;
    private final UserService userService;
    private final ModelMapper modelMapper;

    @Autowired
    public ItemController(ItemService itemService, UserService userService, ModelMapper modelMapper) {
        this.itemService = itemService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/add")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("Add Item")
    public ModelAndView addItem (){
        return super.view("item/add-item");
    }

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ModelAndView addItemConfirm (@ModelAttribute ItemAddBindingModel model){

        this.itemService.addItem(this.modelMapper.map(model, ItemServiceModel.class));

        return super.redirect("/home");
    }
}
