package com.example.demo.mapper;

import com.example.demo.dto.CommentResponse;
import com.example.demo.dto.CreateCommentRequest;
import com.example.demo.model.entity.Comment;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Date;

@Component
public class MongoCommentMapper implements Mapper<Comment, CommentResponse, CreateCommentRequest> {
    public static Document toDocument(Comment comment) {
        var document = new Document();

        document.append("_id", comment.getId() != null ? comment.getId() : new ObjectId());
        document.append("authorEmail", comment.getAuthorEmail());
        document.append("text", comment.getText());
        document.append("createdAt", new Date());
        document.append("replies", Collections.emptyList());

        return document;
    }

    @Override
    public Comment toEntity(CreateCommentRequest request) {
        var comment = new Comment();

        comment.setAuthorEmail(request.getAuthorEmail());
        comment.setText(request.getText());

        return comment;
    }

    @Override
    public CommentResponse toResponse(Comment comment) {
        var response = new CommentResponse();

        response.setId(comment.getId());
        response.setText(comment.getText());
        response.setAuthorEmail(comment.getAuthorEmail());
        response.setCreatedAt(comment.getCreatedAt());

        return response;
    }
}
