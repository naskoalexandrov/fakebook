package com.example.fakebukproject.service;

import com.example.fakebukproject.domain.models.service.PostServiceModel;

import java.util.List;

public interface PostService {

    PostServiceModel posting(PostServiceModel postServiceModel);

    List<PostServiceModel> findPostByAuthor(String author);

    List<PostServiceModel> findAllPosts();

    PostServiceModel findPostById(String id);

    PostServiceModel editPost(String id, PostServiceModel postServiceModel);

    void delete(String id);


}
