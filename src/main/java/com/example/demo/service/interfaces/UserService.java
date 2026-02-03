package com.example.demo.service.interfaces;

import com.example.demo.model.entity.User;

import java.util.List;

public interface UserService {
    User getById(String userId);
    List<User> getAll();
    User create(User user);
    boolean delete(String userId);
    User updatePassword(String userId, String password);
}
