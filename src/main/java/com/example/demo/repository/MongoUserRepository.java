package com.example.demo.repository;

import com.example.demo.dto.UserResponse;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.mapper.MongoUserMapper;
import com.example.demo.model.entity.User;
import com.example.demo.repository.interfaces.UserRepository;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Filters;
import com.mongodb.client.model.FindOneAndUpdateOptions;
import com.mongodb.client.model.ReturnDocument;
import com.mongodb.client.model.Updates;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class MongoUserRepository implements UserRepository {
    private final MongoCollection<Document> userCollection;

    public MongoUserRepository(MongoDatabase mongoDatabase) {
        this.userCollection = mongoDatabase.getCollection("users");
    }

    @Override
    public UserResponse getById(String id) {
        var idFilter = Filters.eq("_id", new ObjectId(id));

        return MongoUserMapper.toResponse(userCollection.find(idFilter).first());
    }

    @Override
    public List<UserResponse> getAll() {
        var userDocuments = new ArrayList<Document>();

        userCollection.find().into(userDocuments);

        return new ArrayList<>(userDocuments.stream().map(MongoUserMapper::toResponse).toList());
    }

    @Override
    public UserResponse insert(User user) {
        var userDocument = new Document()
                .append("_id", new ObjectId())
                .append("name", user.getName())
                .append("email", user.getEmail())
                .append("password", user.getPassword());

        userCollection.insertOne(userDocument);

        user.setId(userDocument.getObjectId("_id").toHexString());

        return MongoUserMapper.toResponse(userDocument);
    }

    @Override
    public boolean delete(String id) {
        var result = userCollection.deleteOne(Filters.eq("_id", new ObjectId(id)));

        return result.getDeletedCount() > 0;
    }

    @Override
    public UserResponse updatePassword(String id, String password) {
        var filter = Filters.eq("_id", new ObjectId(id));

        var updates = Updates.combine(
                Updates.set("password", password)
        );

        FindOneAndUpdateOptions options = new FindOneAndUpdateOptions()
                .returnDocument(ReturnDocument.AFTER);

        Document updatedDoc = userCollection.findOneAndUpdate(filter, updates, options);

        if (updatedDoc == null) {
            throw new ResourceNotFoundException("User not found");
        }

        return MongoUserMapper.toResponse(updatedDoc);
    }
}
