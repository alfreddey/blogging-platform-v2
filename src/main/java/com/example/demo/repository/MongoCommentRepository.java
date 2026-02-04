package com.example.demo.repository;

import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.MongoCommentMapper;
import com.example.demo.model.entity.Comment;
import com.example.demo.repository.interfaces.CommentRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

@Repository
public class MongoCommentRepository implements CommentRepository {
    private MongoCollection<Document> postCollection;

    public MongoCommentRepository(MongoDatabase mongoDatabase) {
        this.postCollection = mongoDatabase.getCollection("posts");
    }

    @Override
    public Comment insert(String postId, Comment comment) {
        var filter = Filters.eq("_id", new ObjectId(postId));
        var commentDocument = MongoCommentMapper.toDocument(comment);
        var update = Updates.push("comments", commentDocument);
        var result = postCollection.updateOne(filter, update);

        if (!result.wasAcknowledged()) {
            throw new ResourceNotFoundException("Post with id: " + postId + " not found");
        }

        return comment;
    }

    @Override
    public boolean delete(String postId, String commentId) {
        var filter = Filters.eq("_id", new ObjectId(postId));
        var update = Updates.pull("comments", new Document("_id", new ObjectId(commentId)));
        var result = postCollection.updateOne(filter, update);

        return result.wasAcknowledged();
    }
}
