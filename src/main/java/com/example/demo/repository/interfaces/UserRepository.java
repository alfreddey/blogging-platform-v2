package com.example.demo.repository.interfaces;

import com.example.demo.dto.UserResponse;
import com.example.demo.model.entity.User;

import java.util.List;

public interface UserRepository {
    UserResponse getById(String id);
    List<UserResponse> getAll();
    UserResponse insert(User user);
    boolean delete(String id);
    UserResponse updatePassword(String id, String password);
}
