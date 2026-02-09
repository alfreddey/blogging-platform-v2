package com.example.demo.mapper;

import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.entity.User;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.stereotype.Component;

@Component
public class MongoUserMapper implements Mapper<User, UserResponse, UserRequest> {
    @Override
    public UserResponse toResponse(User user) {
        if (user == null) {
            return null;
        }

        var userResponse = new UserResponse();

        userResponse.id = user.getId();
        userResponse.email = user.getEmail();
        userResponse.name = user.getName();

        return userResponse;
    }

    @Override
    public User toEntity(UserRequest request) {
        return request == null ? null : map(null, request.name, request.email, request.password);
    }

    public static User toUser(Document userDocument) {
        return userDocument == null ? null : map(
                userDocument.getObjectId("_id").toHexString(),
                userDocument.getString("name"),
                userDocument.getString("email"),
                userDocument.getString("password")
        );
    }

    public static Document toDocument(User user) {
        var doc = new Document()
                .append("_id", new ObjectId())
                .append("name", user.getName())
                .append("email", user.getEmail())
                .append("password", user.getPassword());

        return doc;
    }

    private static User map(String id, String name, String email, String password) {
        var user = new User();

        user.setId(id);
        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}
