package com.example.demo.controller;

import com.example.demo.dto.CreatePostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.mapper.PostMapper;
import com.example.demo.service.interfaces.PostService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class PostGraphQLController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostGraphQLController(PostService postService, PostMapper postMapper) {
        this.postService = postService;
        this.postMapper = postMapper;
    }

    @QueryMapping
    public PostResponse post(@Argument String id) {
        return postMapper.toResponse(postService.getById(id));
    }

    @QueryMapping
    public List<PostResponse> posts() {
        return postService.getAll().stream().map(postMapper::toResponse).toList();
    }

    @MutationMapping
    public PostResponse createPost(@Argument CreatePostRequest input) {
        var post = postMapper.toPost(input);

        return postMapper.toResponse(postService.create(post));
    }

    @MutationMapping
    public boolean deletePost(@Argument String id) {
        return postService.delete(id);
    }
}
