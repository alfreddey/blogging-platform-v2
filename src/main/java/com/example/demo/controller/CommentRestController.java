package com.example.demo.controller;

import com.example.demo.dto.ApiResponse;
import com.example.demo.dto.CommentResponse;
import com.example.demo.dto.CommentRequest;
import com.example.demo.mapper.Mapper;
import com.example.demo.model.entity.Comment;
import com.example.demo.service.interfaces.CommentService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(("${api.base-url}/posts/{postId}/comments"))
public class CommentRestController {
    private final CommentService commentService;
    private final Mapper<Comment, CommentResponse, CommentRequest> commentMapper;

    public CommentRestController(CommentService commentService, Mapper<Comment, CommentResponse, CommentRequest> commentMapper) {
        this.commentService = commentService;
        this.commentMapper = commentMapper;
    }

    @PostMapping
    public ApiResponse<CommentResponse> create(@PathVariable String postId, @Valid @RequestBody CommentRequest request) {
        var comment = commentService.addComment(postId, commentMapper.toEntity(request));
        var commentResponse = commentMapper.toResponse(comment);

        return new ApiResponse<>(HttpStatus.CREATED, "Comment created successfully", commentResponse);
    }

    @DeleteMapping("/{commentId}")
    public ApiResponse<Boolean> delete(@PathVariable String postId, @PathVariable String commentId) {
        boolean deleted = commentService.delete(postId, commentId);
        return new ApiResponse<>(HttpStatus.OK, "Comment deleted successfully", deleted);
    }
}
