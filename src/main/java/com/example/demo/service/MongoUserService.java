package com.example.demo.service;

import com.example.demo.dto.CreateUserRequest;
import com.example.demo.dto.UserResponse;
import com.example.demo.mapper.MongoUserMapper;
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

    public UserResponse getById(String id) {
        return userRepository.getById(id);
    }

    public List<UserResponse> getAll() {
        return userRepository.getAll();
    }

    public UserResponse create(CreateUserRequest request) {
        return userRepository.insert(MongoUserMapper.toUser(request));
    }

    @Override
    public boolean delete(String id) {
        return userRepository.delete(id);
    }

    @Override
    public UserResponse updatePassword(String id, String password) {
        return userRepository.updatePassword(id, password);
    }
}
