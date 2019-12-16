package com.example.fakebukproject.repository;

import com.example.fakebukproject.domain.entities.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, String> {
    Optional<Post> findPostByAuthor(String author);
}
