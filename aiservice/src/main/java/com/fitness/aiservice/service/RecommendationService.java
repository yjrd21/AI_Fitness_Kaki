package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import com.fitness.aiservice.repository.RecommendationRepository;
import com.fitness.aiservice.model.Recommendation;
import java.util.List;

@Service 
@RequiredArgsConstructor 
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {
        return recommendationRepository.findByUserId(userId);
    }

    public  Recommendation  getActivityRecommendation(String activityId) {
        return recommendationRepository.findByActivityId(activityId)
        .orElseThrow(() -> new RuntimeException("Recommendation not found for activity: " + activityId));
    }
}
