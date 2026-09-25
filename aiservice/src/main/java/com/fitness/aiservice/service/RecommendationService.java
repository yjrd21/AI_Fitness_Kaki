package com.fitness.aiservice.service;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import com.fitness.aiservice.repository.RecommendationRepository;
import com.fitness.aiservice.model.Recommendation;
import java.util.List;

@Service 
@RequiredArgsConstructor 
@Slf4j
public class RecommendationService {
    private final RecommendationRepository recommendationRepository;

    public List<Recommendation> getUserRecommendation(String userId) {
        log.info("Fetching recommendations for userId={}", userId);
        List<Recommendation> recommendations = recommendationRepository.findByUserId(userId);
        log.info("Fetched {} recommendations for userId={}", recommendations.size(), userId);
        return recommendations;
    }

    public  Recommendation  getActivityRecommendation(String activityId) {
        log.info("Fetching recommendation with activityId={}", activityId);
        return recommendationRepository.findByActivityId(activityId)
        .orElseThrow(() -> {
            log.warn("Recommendation not found with activityId={}", activityId);
            return new RuntimeException("Recommendation not found for activity: " + activityId);
        });
    }
}
