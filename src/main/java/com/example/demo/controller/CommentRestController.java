package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CommentResponse;
import com.example.demo.dto.CreateCommentRequest;
import com.example.demo.mapper.CommentMapper;
import com.example.demo.service.interfaces.CommentService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

// TODO: Add openapi documentation
// TODO: Add endpoints for getting, updating, and deleting comments
// TODO: Organize dtos into subpackages

@RestController
@RequestMapping(("api/v1/{postId}/comments"))
public class CommentRestController {
    private final CommentService commentService;
    private final CommentMapper commentMapper;

    public CommentRestController(CommentService commentService, CommentMapper commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping
    public ApiResponse<CommentResponse> create(@PathVariable String postId, @RequestBody CreateCommentRequest request) {
        var comment = commentService.addComment(postId, commentMapper.toComment(request));
        var commentResponse = commentMapper.toResponse(comment);

        return new ApiResponse<>(HttpStatus.CREATED, "Comment created successfully", commentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Boolean> delete(@PathVariable String postId, @PathVariable String commentId) {
        boolean deleted = commentService.delete(postId, commentId);
        return new ApiResponse<>(HttpStatus.OK, "Comment deleted successfully", deleted);
    }
}
