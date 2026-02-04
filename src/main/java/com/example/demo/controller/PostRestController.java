package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CreatePostRequest;
import com.example.demo.dto.PostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.mapper.PostMapper;
import com.example.demo.service.interfaces.PostService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// TODO: Add openapi documentation

@RestController
@RequestMapping("api/v1/posts")
public class PostRestController {
    private final PostService postService;
    private final PostMapper postMapper;

    public PostRestController(PostService postService, PostMapper postMapper) {
        this.postMapper = postMapper;
        this.postService = postService;
    }

    @GetMapping
    public ApiResponse<List<PostResponse>> getAll() {
        var posts = postService.getAll()
                .stream()
                .map(postMapper::toResponse)
                .toList();

        return new ApiResponse<>(HttpStatus.OK, "Posts retrieved successfully", posts);
    }

    @GetMapping("/{postId}")
    public ApiResponse<PostResponse> getById(@PathVariable String postId) {
        return new ApiResponse<>(HttpStatus.OK, "Post retrieved successfully", postMapper.toResponse(postService.getById(postId)));
    }

    @PostMapping
    public ApiResponse<PostResponse> create(@RequestBody CreatePostRequest request) {
        var post = postMapper.toPost(request);

        return new ApiResponse<>(HttpStatus.CREATED, "Post created successfully", postMapper.toResponse(postService.create(post)));
    }

    @DeleteMapping("/{postId}")
    public ApiResponse<Boolean> delete(@PathVariable String postId) {
        return new ApiResponse<>(HttpStatus.OK, "Post deleted successfully", postService.delete(postId));
    }

    @PatchMapping("/{postId}")
    public ApiResponse<PostResponse> updatePostContent(@PathVariable String postId, @RequestBody PostRequest request) {
        var post = postService.updatePostContent(postId, request.getContent());

        return new ApiResponse<>(HttpStatus.OK, "Post content updated successfully", postMapper.toResponse(post));
    }
}
