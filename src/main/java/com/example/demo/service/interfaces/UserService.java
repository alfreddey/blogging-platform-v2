package com.example.demo.service.interfaces;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserResponse;

import java.util.List;

public interface UserService {
    UserResponse getById(String email);
    List<UserResponse> getAll();
    UserResponse create(CreateUserRequest request);
    boolean delete(String id);
    UserResponse updatePassword(String id, String password);
}
