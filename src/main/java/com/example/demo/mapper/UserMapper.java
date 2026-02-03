package com.example.demo.mapper;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.model.entity.User;

public interface UserMapper {
    UserResponse toResponse(User user);
    User toUser(CreateUserRequest userResponse);
}
