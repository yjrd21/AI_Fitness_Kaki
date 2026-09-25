package com.fitness.userservice.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.userservice.dto.RegisterRequest;
import com.fitness.userservice.dto.UserResponse;

import jakarta.validation.Valid;
import com.fitness.userservice.service.UserService;
import lombok.extern.slf4j.Slf4j;

@RestController 
@RequestMapping("/api/users")
@AllArgsConstructor
@Slf4j
public class UserController {

    private UserService userService;

    // Return DTO for user profile
    @GetMapping("/{userId}")
    public ResponseEntity<UserResponse> getUserProfile(@PathVariable String userId) {
        log.info("GET /api/users/{} received", userId);
        return ResponseEntity.ok(userService.getUserProfile(userId));
    }

    @PostMapping("/register")
    public ResponseEntity<UserResponse> register(@Valid @RequestBody RegisterRequest request) {
        log.info("POST /api/users/register received for email={}", request.getEmail());
        return ResponseEntity.ok(userService.register(request));
    }

    @GetMapping("/{userId}/validate")
    public ResponseEntity<Boolean> validateUser(@PathVariable String userId) {
        log.info("GET /api/users/{}/validate received", userId);
        return ResponseEntity.ok(userService.existByUserId(userId));
    }


}
