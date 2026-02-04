package com.example.demo.service;

import com.example.demo.model.entity.Post;
import com.example.demo.repository.interfaces.PostRepository;
import com.example.demo.service.interfaces.PostService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoPostService implements PostService {
    private final PostRepository postRepository;

    public MongoPostService(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public List<Post> getAll() {
        return postRepository.getAll();
    }

    @Override
    public Post getById(String postId) {
        return postRepository.getById(postId);
    }

    @Override
    public Post create(Post post) {
        return postRepository.insert(post);
    }

    @Override
    public boolean delete(String postId) {
        return postRepository.delete(postId);
    }

    @Override
    public Post updatePostContent(String id, String content) {
        return postRepository.updatePostContent(id, content);
    }
}
