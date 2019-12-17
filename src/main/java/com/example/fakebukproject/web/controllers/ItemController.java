package com.example.fakebukproject.web.controllers;

import com.example.fakebukproject.domain.models.bindings.ItemAddBindingModel;
import com.example.fakebukproject.domain.models.service.ItemServiceModel;
import com.example.fakebukproject.domain.models.view.ItemViewModel;
import com.example.fakebukproject.domain.models.view.UserProfileViewModel;
import com.example.fakebukproject.error.ItemNotFoundException;
import com.example.fakebukproject.service.ItemService;
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

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Items")
    public ModelAndView showAll(ModelAndView modelAndView){
        List<ItemServiceModel> items = this.itemService.findAllItems()
                .stream()
                .map(i -> this.modelMapper.map(i, ItemServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("items", items);
        return super.view("item/all-items", modelAndView);
    }

    @GetMapping("/all-by-user")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Items By User")
    public ModelAndView showAllByUser(Principal principal, ModelAndView modelAndView){
        List<ItemServiceModel> items = this.itemService.findAllItems()
                .stream()
                .filter(i -> i.getSeller().equals(principal.getName()))
                .map(i -> this.modelMapper.map(i, ItemServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("items", items);
        return super.view("item/all-items-by-user", modelAndView);
    }

    @GetMapping("/delete/{id}")
    public ModelAndView deleteItem(@PathVariable String id, ModelAndView modelAndView) {
        ItemServiceModel itemServiceModel = this.itemService.findById(id);

        modelAndView.addObject("item", itemServiceModel);
        modelAndView.addObject("itemId", id);

        return super.view("item/delete-item", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PageTitle("Delete Item")
    public ModelAndView deleteItemConfirm(@PathVariable String id) {
        this.itemService.deleteItem(id);

        return super.redirect("/items/all-by-user");
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Edit Item")
    public ModelAndView editItem(@PathVariable String id, ModelAndView modelAndView){
        ItemServiceModel itemServiceModel = this.itemService.findById(id);

        modelAndView.addObject("item", itemServiceModel);
        modelAndView.addObject("itemId", id);

        return super.view("item/edit-item",modelAndView);

    }

    @PostMapping("/edit/{id}")
    public ModelAndView editItemConfirm(@PathVariable String id,@ModelAttribute ItemAddBindingModel model){
        this.itemService.editItem(id, this.modelMapper.map(model, ItemServiceModel.class));

        return super.redirect("/items/all-by-user");
    }


    @GetMapping("/all-items")
    @ResponseBody
    public List<ItemViewModel> fetchAllItems() {
        return this.itemService.findAllItems()
                .stream()
                .map(i -> this.modelMapper.map(i, ItemViewModel.class))
                .collect(Collectors.toList());
    }

    @ExceptionHandler({ItemNotFoundException.class})
    public ModelAndView handleVideoNotFound(ItemNotFoundException e) {
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
