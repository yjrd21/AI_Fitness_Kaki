package com.fitness.userservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;
import com.fitness.userservice.model.User;
import com.fitness.userservice.repository.UserRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j 
public class UserService {

    @Autowired 
    private UserRepository repository;

    public UserResponse register(RegisterRequest request) {
        log.info("Registering user with email={}", request.getEmail());

        if (repository.existsByEmail(request.getEmail())) {
            log.warn("Registration rejected because email already exists: {}", request.getEmail());
            throw new RuntimeException("Email already exists");
        }

        User user = new User();
        user.setEmail(request.getEmail());
        user.setPassword(request.getPassword());
        user.setFirstName(request.getFirstName());
        user.setLastName(request.getLastName());

        User savedUser = repository.save(user);
        UserResponse userResponse = new UserResponse();
        userResponse.setId(savedUser.getId());
        userResponse.setEmail(savedUser.getEmail());
        userResponse.setPassword(savedUser.getPassword());
        userResponse.setFirstName(savedUser.getFirstName());
        userResponse.setLastName(savedUser.getLastName());
        userResponse.setCreatedAt(savedUser.getCreatedAt());
        userResponse.setUpdatedAt(savedUser.getUpdatedAt());

        log.info("User registered with userId={}", savedUser.getId());
        return userResponse;
    }

    public UserResponse getUserProfile(String userId) {
        log.info("Fetching profile for userId={}", userId);
        User user = repository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UserResponse userResponse = new UserResponse();
        userResponse.setId(user.getId());
        userResponse.setEmail(user.getEmail());
        userResponse.setPassword(user.getPassword());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setCreatedAt(user.getCreatedAt());
        userResponse.setUpdatedAt(user.getUpdatedAt());

        log.info("Fetched profile for userId={}", userId);
        return userResponse;
    }

    public Boolean existByUserId(String userId) {
        log.info("Validating userId={}", userId);
        Boolean exists = repository.existsById(userId);
        log.info("User validation completed for userId={}, exists={}", userId, exists);
        return exists;
    }
}