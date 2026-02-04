package com.example.demo.service;

import com.example.demo.model.entity.Comment;
import com.example.demo.repository.interfaces.CommentRepository;
import com.example.demo.service.interfaces.CommentService;
import org.springframework.stereotype.Service;

@Service
public class MongoCommentService implements CommentService {
    private final CommentRepository commentRepository;

    public MongoCommentService(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment addComment(String postId, Comment comment) {
        return commentRepository.insert(postId, comment);
    }

    @Override
    public boolean delete(String postId, String commentId) {
        return commentRepository.delete(postId, commentId);
    }
}
