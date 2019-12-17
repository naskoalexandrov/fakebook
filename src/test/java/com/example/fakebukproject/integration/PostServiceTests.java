package com.example.fakebukproject.integration;

import com.example.fakebukproject.repository.PostRepository;
import com.example.fakebukproject.service.PostService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@SpringBootTest
@RunWith(SpringRunner.class)
public class PostServiceTests {

    @Autowired
    private PostService service;

    @MockBean
    private PostRepository mockPostRepository;

    @Test(expected = IllegalArgumentException.class)
    public void postingWithInvalidValues_ThrowError() {
        service.posting(null);
        verify(mockPostRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findPostByAuthorWithInvalidValues_ThrowError() {
        service.findPostByAuthor(null);
        verify(mockPostRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findPostByIdWithInvalidValues_ThrowError() {
        service.findPostById(null);
        verify(mockPostRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void findAllPostsWithInvalidValues_ThrowError() {
        service.findAllPosts();
        verify(mockPostRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deleteWithInvalidValues_ThrowError() {
        service.delete(null);
        verify(mockPostRepository)
                .save(any());
    }

    @Test(expected = IllegalArgumentException.class)
    public void editPostWithInvalidValues_ThrowError() {
        service.editPost(null, null);
        verify(mockPostRepository)
                .save(any());
    }
}
