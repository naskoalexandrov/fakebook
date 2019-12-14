package com.example.fakebukproject.service;

import com.example.fakebukproject.domain.models.service.PostServiceModel;

import java.util.List;

public interface PostService {

    PostServiceModel posting(PostServiceModel postServiceModel);

    PostServiceModel findPostByText(String text);

    List<PostServiceModel> findPostByAuthor(String author);

    List<PostServiceModel> findAllPosts();
}
