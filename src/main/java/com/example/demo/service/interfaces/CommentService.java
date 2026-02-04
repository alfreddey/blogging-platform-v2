package com.example.demo.service.interfaces;

import com.example.demo.model.entity.Comment;

public interface CommentService {
    public Comment addComment(String postId, Comment comment);
    boolean delete(String postId, String commentId);
}
