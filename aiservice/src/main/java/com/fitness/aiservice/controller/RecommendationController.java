package com.fitness.aiservice.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import com.fitness.aiservice.service.RecommendationService;

import org.springframework.http.ResponseEntity;
import java.util.List;
import com.fitness.aiservice.model.Recommendation;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.extern.slf4j.Slf4j;


@RestController 
@RequiredArgsConstructor 
@Slf4j
@RequestMapping ("/api/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<Recommendation>> getUserRecommendation(@PathVariable  String userId) {
        log.info("GET /api/recommendations/user/{} received", userId);
        return ResponseEntity.ok(recommendationService.getUserRecommendation(userId));
    }

    @GetMapping("/activity/{activityId}")
    public ResponseEntity<Recommendation> getActivityRecommendation(@PathVariable  String activityId) {
        log.info("GET /api/recommendations/activity/{} received", activityId);
        return ResponseEntity.ok(recommendationService.getActivityRecommendation(activityId));
    }

}
