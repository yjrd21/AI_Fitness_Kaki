package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class ActivityAIService {
    private final OpenAIService openAIService;

    public String generateRecommendation(Activity activity) {
        String prompt = createPromptForActivity(activity);
        String aiResponse = openAIService.getAnswer(prompt);
        log.info("Generated AI recommendation for activityId={}: {}", activity.getId(), aiResponse);
        return aiResponse;
    }

    private String createPromptForActivity(Activity activity) {
        return String.format(
                "Given the following activity details:\n" +
                        "Activity ID: %s\n" +
                        "User ID: %s\n" +
                        "Duration: %d minutes\n" +
                        "Type: %s\n" +
                        "Calories Burned: %d\n" +
                        "Please provide a personalized recommendation for this activity in the follow format: 'Recommendation: <your recommendation here>'.",
                activity.getId(),
                activity.getUserId(),
                activity.getDuration(),
                activity.getType(),
                activity.getCaloriesBurned());
    }
}
