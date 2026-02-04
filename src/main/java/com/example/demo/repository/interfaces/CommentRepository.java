package com.example.demo.repository.interfaces;

import com.example.demo.model.entity.Comment;

public interface CommentRepository {
    Comment insert(String id, Comment comment);
    boolean delete(String postId, String commentId);
}
