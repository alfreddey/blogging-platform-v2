package com.example.demo.service.interfaces;

import com.example.demo.model.entity.Post;

import java.util.List;

public interface PostService {
    Post getById(String postId);
    List<Post> getAll();
    Post create(Post post);
    boolean delete(String id);
    Post updatePostContent(String id, String content);
    Post search(String term);
}
