package com.example.demo.repository.interfaces;

import com.example.demo.model.entity.Post;

import java.util.List;

public interface PostRepository {
    Post getById(String postId);
    List<Post> getAll();
    Post insert(Post post);
    boolean delete(String postId);
    Post updatePostContent(String id, String content);
}
