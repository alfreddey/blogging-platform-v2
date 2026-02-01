package com.example.demo.mapper;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.entity.User;
import org.bson.Document;
import org.springframework.stereotype.Component;

@Component
public class MongoUserMapper {
    public static UserResponse toResponse(Document userDocument) {
        if (userDocument == null) {
            return null;
        }

        var userResponse = new UserResponse();

        userResponse.id = userDocument.getObjectId("_id").toHexString();
        userResponse.email = userDocument.getString("email");
        userResponse.name = userDocument.getString("name");

        return userResponse;
    }

    public static User toUser(CreateUserRequest request) {
        return request == null ? null : map(request.name, request.email, request.password);
    }

    private static User map(String name, String email, String password) {
        var user = new User();

        user.setName(name);
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}
