package com.example.fakebukproject.web.controllers;

import com.example.fakebukproject.domain.models.bindings.PostBindingModel;
import com.example.fakebukproject.domain.models.service.PostServiceModel;
import com.example.fakebukproject.domain.models.view.PostViewModel;
import com.example.fakebukproject.domain.models.view.UserProfileViewModel;
import com.example.fakebukproject.error.PostNotFoundException;
import com.example.fakebukproject.service.PostService;
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

    @GetMapping("/all-by-user")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Posts By User")
    public ModelAndView showAllByUser(Principal principal, ModelAndView modelAndView){

        modelAndView
                .addObject("model", this.modelMapper.
                        map(this.postService.findPostByAuthor(principal.getName()), PostViewModel.class));

        List<PostServiceModel> posts = this.postService.findAllPosts()
                .stream()
                .filter(p -> p.getAuthor().equals(principal.getName()))
                .map(p -> this.modelMapper.map(p, PostServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("posts", posts);
        return super.view("posting/all-posts-by-user", modelAndView);
    }

    @GetMapping("/all")
    @PreAuthorize("isAuthenticated()")
    @PageTitle("All Posts")
    public ModelAndView showAll(Principal principal, ModelAndView modelAndView){

        modelAndView
                .addObject("model", this.modelMapper.
                        map(this.postService.findPostByAuthor(principal.getName()), PostViewModel.class));

        List<PostServiceModel> posts = this.postService.findAllPosts()
                .stream()
                .map(p -> this.modelMapper.map(p, PostServiceModel.class))
                .collect(Collectors.toList());

        modelAndView.addObject("posts", posts);
        return super.view("posting/all-posts", modelAndView);
    }

    @GetMapping("/edit/{id}")
    @PageTitle("Edit Post")
    public ModelAndView editPost(@PathVariable String id, ModelAndView modelAndView){
        PostServiceModel postServiceModel = this.postService.findPostById(id);

        modelAndView.addObject("post", postServiceModel);
        modelAndView.addObject("postId", id);

        return super.view("posting/edit-post",modelAndView);

    }

    @PostMapping("/edit/{id}")
    public ModelAndView editPostConfirm(@PathVariable String id,@ModelAttribute PostBindingModel model){
        this.postService.editPost(id, this.modelMapper.map(model, PostServiceModel.class));

        return super.redirect("/posts/all-by-user");
    }


    @GetMapping("/delete/{id}")
    public ModelAndView deletePost(@PathVariable String id, ModelAndView modelAndView) {
        PostServiceModel postServiceModel = this.postService.findPostById(id);

        modelAndView.addObject("post", postServiceModel);
        modelAndView.addObject("postId", id);

        return super.view("posting/delete-post", modelAndView);
    }

    @PostMapping("/delete/{id}")
    @PageTitle("Delete Post")
    public ModelAndView deletePostConfirm(@PathVariable String id) {
        this.postService.delete(id);

        return super.redirect("/posts/all-by-user");
    }

    @ExceptionHandler({PostNotFoundException.class})
    public ModelAndView handlePostNotFound(PostNotFoundException e) {
        ModelAndView modelAndView = new ModelAndView("error");
        modelAndView.addObject("message", e.getMessage());
        modelAndView.addObject("statusCode", e.getStatus());

        return modelAndView;
    }

    @GetMapping("/all-posts")
    @ResponseBody
    public List<PostViewModel> fetchAllPosts() {
        return this.postService.findAllPosts()
                .stream()
                .map(p -> this.modelMapper.map(p, PostViewModel.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/all-posts-by-user")
    @ResponseBody
    public List<PostViewModel> fetchAllPostsByUser() {
        return this.postService.findAllPosts()
                .stream()
                .map(p -> this.modelMapper.map(p, PostViewModel.class))
                .collect(Collectors.toList());
    }

    @InitBinder
    private void initBinder(WebDataBinder webDataBinder) {
        webDataBinder.registerCustomEditor(String.class, new StringTrimmerEditor(true));
    }

}
