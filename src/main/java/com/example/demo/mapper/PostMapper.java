package com.example.demo.mapper;

import com.example.demo.dto.CreatePostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.model.entity.Post;

public interface PostMapper {
    PostResponse toResponse(Post post);
    Post toPost(CreatePostRequest request);
}
