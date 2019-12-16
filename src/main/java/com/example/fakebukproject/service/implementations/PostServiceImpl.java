package com.example.fakebukproject.service.implementations;

import com.example.fakebukproject.domain.entities.Post;
import com.example.fakebukproject.domain.models.service.LogServiceModel;
import com.example.fakebukproject.domain.models.service.PostServiceModel;
import com.example.fakebukproject.error.Constants;
import com.example.fakebukproject.error.PostNotFoundException;
import com.example.fakebukproject.repository.PostRepository;
import com.example.fakebukproject.service.LogService;
import com.example.fakebukproject.service.PostService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

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
    public List<PostServiceModel> findPostByAuthor(String author) {
        return this.postRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PostServiceModel.class))
                .filter(p -> p.getAuthor().equals(author))
                .collect(Collectors.toList());
    }

    @Override
    public List<PostServiceModel> findAllPosts() {
        return this.postRepository.findAll()
                .stream()
                .map(p -> this.modelMapper.map(p, PostServiceModel.class))
                .collect(Collectors.toList());
    }

    @Override
    public PostServiceModel findPostById(String id) {
        return this.postRepository.findById(id)
                .map(p -> this.modelMapper.map(p, PostServiceModel.class))
                .orElseThrow(() -> new PostNotFoundException(Constants.POST_ID_NOT_FOUND));
    }

    @Override
    public PostServiceModel editPost(String id, PostServiceModel postServiceModel) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(Constants.POST_ID_NOT_FOUND));

        post.setAuthor(postServiceModel.getAuthor());
        post.setText(postServiceModel.getText());

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(postServiceModel.getAuthor());
        logServiceModel.setDescription("Post edited");
        logServiceModel.setTime(LocalDateTime.now());

        return this.modelMapper.map(this.postRepository.saveAndFlush(post), PostServiceModel.class);
    }

    @Override
    public void delete(String id) {
        Post post = this.postRepository.findById(id)
                .orElseThrow(() -> new PostNotFoundException(Constants.POST_ID_NOT_FOUND));

        LogServiceModel logServiceModel = new LogServiceModel();
        logServiceModel.setUsername(post.getAuthor());
        logServiceModel.setDescription("Post deleted");
        logServiceModel.setTime(LocalDateTime.now());

        this.logService.putLogInDatabase(logServiceModel);

        this.postRepository.delete(post);
    }
}
