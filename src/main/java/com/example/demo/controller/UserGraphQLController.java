package com.example.demo.controller;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.service.interfaces.UserService;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class UserGraphQLController {
    private final UserService userService;

    public UserGraphQLController(UserService userService) {
        this.userService = userService;
    }

    @QueryMapping
    public UserResponse user(@Argument String id) {
        return userService.getById(id);
    }

    @QueryMapping
    public List<UserResponse> users() {
        return userService.getAll();
    }

    @MutationMapping
    public UserResponse createUser(@Argument CreateUserRequest input) {
        return userService.create(input);
    }

    @MutationMapping
    public boolean deleteUser(@Argument String id) {
        return userService.delete(id);
    }

    @MutationMapping
    public UserResponse updateUserPassword(@Argument String id, @Argument String password) {
        return userService.updatePassword(id, password);
    }
}
