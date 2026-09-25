package com.fitness.activityservice.service;
import com.fitness.activityservice.dto.ActivityResponse;
import org.springframework.stereotype.Service;
import com.fitness.activityservice.repository.ActivityRepository;
import com.fitness.activityservice.model.Activity;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import com.fitness.activityservice.dto.ActivityRequest;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
/**
 * ActivityService
 */
@Service
@RequiredArgsConstructor 
@Slf4j
public class ActivityService {

    private final ActivityRepository activityRepository;
    private final UserValidationService userValidationService;
    private final RabbitTemplate rabbitTemplate;

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    public ActivityResponse trackActivity(ActivityRequest request) {
        log.info("Tracking activity for userId={}, type={}", request.getUserId(), request.getType());
        boolean isValidUser = userValidationService.validateUser(request.getUserId());
        if (!isValidUser) {
            log.warn("Activity rejected because userId={} is invalid", request.getUserId());
            throw new RuntimeException("Invalid userId: " + request.getUserId());
        }
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        
        // Save the activity to DB
        Activity savedActivity = activityRepository.save(activity);
        log.info("Activity saved with activityId={} for userId={}", savedActivity.getId(), savedActivity.getUserId());

        // Publish the activity to RabbitMQ for AI processing
        try{
            rabbitTemplate.convertAndSend(exchange, routingKey, savedActivity);
            log.info("Published activityId={} to RabbitMQ exchange={} with routingKey={}", savedActivity.getId(), exchange, routingKey);
        } catch (Exception e) {
            log.error("Failed to publish activityId={} to RabbitMQ: {}", savedActivity.getId(), e.getMessage());
        }

        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
        log.debug("Mapping activityId={} to response", activity.getId());
        ActivityResponse response = new ActivityResponse();
        response.setId(activity.getId());
        response.setUserId(activity.getUserId());
        response.setType(activity.getType());
        response.setDuration(activity.getDuration());
        response.setCaloriesBurned(activity.getCaloriesBurned());
        response.setStartTime(activity.getStartTime());
        response.setAdditionalMetrics(activity.getAdditionalMetrics());
        response.setCreatedAt(activity.getCreatedAt());
        response.setUpdatedAt(activity.getUpdatedAt());
        log.debug("Mapped activityId={} to response", activity.getId());
        return response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        log.info("Fetching activities for userId={}", userId);
        List<Activity> activities = activityRepository.findByUserId(userId);
        List<ActivityResponse> responses = activities.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
        log.info("Fetched {} activities for userId={}", responses.size(), userId);
        return responses;
    }

    public ActivityResponse getActivityById(String activityId) {
        log.info("Fetching activity with activityId={}", activityId);
        ActivityResponse response = activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> {
                    log.warn("Activity not found with activityId={}", activityId);
                    return new RuntimeException("Activity not found with id: " + activityId);
                });
        log.info("Fetched activity with activityId={}", activityId);
        return response;
    }

    
}
