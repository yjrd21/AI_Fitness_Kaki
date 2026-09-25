package com.fitness.activityservice.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.springframework.http.HttpStatus;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j 
public class UserValidationService {
    private final WebClient userServiceWebClient;

    public boolean validateUser(String userId) {
        log.info("Validating userId={} through User Service", userId);
        try {
            Boolean valid = userServiceWebClient
                    .get()
                    .uri("/api/users/{userId}/validate", userId)
                    .retrieve()
                    .bodyToMono(Boolean.class)
                    .block();
            log.info("User validation completed for userId={}, valid={}", userId, valid);
            return Boolean.TRUE.equals(valid);
        } catch (WebClientResponseException e) {
            if (e.getStatusCode() == HttpStatus.NOT_FOUND) {
                log.warn("User Service could not find userId={}", userId);
                throw new RuntimeException("User not found with id: " + userId);
            } else if (e.getStatusCode() == HttpStatus.BAD_REQUEST) {
                log.warn("User Service rejected userId={} as invalid", userId);
                throw new RuntimeException("Invalid userId: " + userId);
            } 
            log.error("User validation failed for userId={} with status={}", userId, e.getStatusCode(), e);
            return false;
        }

    }

}
