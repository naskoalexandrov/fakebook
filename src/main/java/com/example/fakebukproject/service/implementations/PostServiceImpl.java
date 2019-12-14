package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Post;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.domain.models.service.PostServiceModel;
import com.example.fakebukproject.repository.PostRepository;
import com.example.fakebukproject.service.LogService;
import com.example.fakebukproject.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final LogService logService;
    private final PostRepository postRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public PostServiceImpl(LogService logService, PostRepository postRepository, ModelMapper modelMapper) {
        this.logService = logService;
        this.postRepository = postRepository;
        this.modelMapper = modelMapper;
    }


    @Override
    public PostServiceModel posting(PostServiceModel postServiceModel) {
        Post post = this.modelMapper.map(postServiceModel, Post.class);

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(postServiceModel.getAuthor().toLowerCase());
        logServiceModel.setDescription(postServiceModel.getText());
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        return this.modelMapper.map(this.postRepository.saveAndFlush(post), PostServiceModel.class);
    }

    @Override
    public PostServiceModel findPostByText(String text) {
        return null;
    }

    @Override
    public List<PostServiceModel> findPostByAuthor(String author) {
        return null;
    }

    @Override
    public List<PostServiceModel> findAllPosts() {
        return null;
    }
}
