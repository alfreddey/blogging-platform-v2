package com.example.demo.repository.interfaces;

import com.example.demo.model.entity.User;

import java.util.List;

public interface UserRepository {
    User getById(String userId);
    List<User> getAll();
    User insert(User user);
    boolean delete(String userId);
    User updatePassword(String userId, String password);
}
