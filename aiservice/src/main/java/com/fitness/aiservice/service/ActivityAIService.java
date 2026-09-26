package com.fitness.aiservice.service;

import com.fitness.aiservice.model.Activity;

import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        processAIResponse(activity, aiResponse);
        return aiResponse;
    }

    private void processAIResponse(Activity activity, String aiResponse) {
       try{
            ObjectMapper mapper = new ObjectMapper();
            JsonNode rootNode = mapper.readTree(aiResponse);
            JsonNode textNode = rootNode.path("output")
            .get(1)
            .path("content")
            .get(0)
            .path("text");
            String recommendationText = textNode.asText().trim();
            log.info("Parsed Content for activityId={}: {}", activity.getId(), recommendationText);

       } catch (Exception e) {
            e.printStackTrace();
        }
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
