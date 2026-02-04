package com.example.demo.mapper;

import com.example.demo.dto.CommentResponse;
import com.example.demo.dto.CreateCommentRequest;
import com.example.demo.model.entity.Comment;

public interface CommentMapper {
    Comment toComment(CreateCommentRequest request);
    CommentResponse toResponse(Comment comment);
}
