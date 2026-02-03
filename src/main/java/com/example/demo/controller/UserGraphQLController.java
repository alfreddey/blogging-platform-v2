package com.example.demo.controller;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.mapper.UserMapper;
import com.example.demo.service.interfaces.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserGraphQLController {
    private final UserService userService;
    private final UserMapper userMapper;

    public UserGraphQLController(UserService userService, UserMapper userMapper) {
        this.userMapper = userMapper;
        this.userService = userService;
    }

    @QueryMapping
    public UserResponse user(@Argument String id) {
        return userMapper.toResponse(userService.getById(id));
    }

    @QueryMapping
    public List<UserResponse> users() {
        return userService.getAll().stream().map(userMapper::toResponse).toList();
    }

    @MutationMapping
    public UserResponse createUser(@Argument CreateUserRequest input) {
        return userMapper.toResponse(userService.create(userMapper.toUser(input)));
    }

    @MutationMapping
    public boolean deleteUser(@Argument String id) {
        return userService.delete(id);
    }

    @MutationMapping
    public UserResponse updateUserPassword(@Argument String id, @Argument String password) {
        return userMapper.toResponse(userService.updatePassword(id, password));
    }
}
