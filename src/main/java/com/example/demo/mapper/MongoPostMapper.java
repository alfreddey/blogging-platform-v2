package com.example.demo.mapper;

import com.example.demo.dto.CreatePostRequest;
import com.example.demo.dto.PostResponse;
import com.example.demo.model.entity.Comment;
import com.example.demo.model.entity.Post;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

@Component
public class MongoPostMapper implements PostMapper {
    @Override
    public PostResponse toResponse(Post post) {
        var response = new PostResponse();

        response.setId(post.getId());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());
        response.setAuthorEmail(post.getAuthorEmail());
        response.setTags(post.getTags());
        response.setCreatedAt(post.getCreatedAt());

        return response;
    }

    @Override
    public Post toPost(CreatePostRequest request) {
        var post = new Post();

        post.setTitle(request.getTitle());
        post.setContent(request.getContent());
        post.setAuthorEmail(request.getAuthorEmail());
        post.setTags(request.getTags() != null ? request.getTags() : Collections.emptyList());
        post.setComments(Collections.emptyList());

        return post;
    }

    public static Post toPost(Document doc) {
        var post = new Post();

        post.setId(doc.getObjectId("_id").toHexString());
        post.setTitle(doc.getString("title"));
        post.setContent(doc.getString("content"));
        post.setAuthorEmail(doc.getString("authorEmail"));
        post.setCreatedAt(doc.getDate("createdAt"));

        var tags = doc.getList("tags", String.class);

        post.setTags(tags != null ? tags : Collections.emptyList());

        var comments = doc.getList("comments", Document.class);

        if (comments != null) {
            var commentList = new ArrayList<>(
                    comments.stream()
                            .map(MongoPostMapper::mapComment)
                            .toList()
            );

            post.setComments(commentList);
        } else {
            post.setComments(Collections.emptyList());
        }

        return post;
    }

    public static Document toDocument(Post post) {
        var doc = new Document()
                .append("_id", new ObjectId())
                .append("title", post.getTitle())
                .append("content", post.getContent())
                .append("authorEmail", post.getAuthorEmail())
                .append("tags", post.getTags());

        var commentDocs = post.getComments().stream()
                .map(comment -> new Document()
                        .append("authorEmail", comment.getAuthorEmail())
                        .append("text", comment.getText())
                        .append("_id", new ObjectId()))
                .toList();

        doc.append("comments", commentDocs);
        doc.append("createdAt", new Date());

        return doc;
    }

    private static Comment mapComment(Document commentDoc) {
        var comment = new Comment();

        comment.setId(commentDoc.getObjectId("_id").toHexString());
        comment.setAuthorEmail(commentDoc.getString("authorEmail"));
        comment.setText(commentDoc.getString("text"));

        return comment;
    }
}
