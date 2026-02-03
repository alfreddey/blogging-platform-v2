package com.example.demo.service;

import com.example.demo.model.entity.User;
import com.example.demo.repository.interfaces.UserRepository;
import com.example.demo.service.interfaces.UserService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MongoUserService implements UserService {
    private final UserRepository userRepository;

    public MongoUserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User getById(String id) {
        return userRepository.getById(id);
    }

    public List<User> getAll() {
        return userRepository.getAll();
    }

    public User create(User user) {
        return userRepository.insert(user);
    }

    @Override
    public boolean delete(String id) {
        return userRepository.delete(id);
    }

    @Override
    public User updatePassword(String id, String password) {
        return userRepository.updatePassword(id, password);
    }
}
