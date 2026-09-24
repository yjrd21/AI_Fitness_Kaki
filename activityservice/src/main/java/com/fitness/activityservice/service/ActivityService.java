package com.fitness.activityservice.service;
import com.fitness.activityservice.dto.ActivityResponse;
import org.springframework.stereotype.Service;
import com.fitness.activityservice.repository.ActivityRepository;
import com.fitness.activityservice.model.Activity;

import org.jspecify.annotations.Nullable;
import org.springframework.http.ResponseEntity;


import lombok.RequiredArgsConstructor;

import com.fitness.activityservice.dto.ActivityRequest;
import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;
/**
 * ActivityService
 */
@Service
@RequiredArgsConstructor 
public class ActivityService {

    private final ActivityRepository activityRepository;

    public ActivityResponse trackActivity(ActivityRequest request) {
        Activity activity = Activity.builder()
                .userId(request.getUserId())
                .type(request.getType())
                .duration(request.getDuration())
                .caloriesBurned(request.getCaloriesBurned())
                .startTime(request.getStartTime())
                .additionalMetrics(request.getAdditionalMetrics())
                .build();
        Activity savedActivity = activityRepository.save(activity);
        return mapToResponse(savedActivity);
    }

    private ActivityResponse mapToResponse(Activity activity) {
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
        return response;
    }

    public List<ActivityResponse> getUserActivities(String userId) {
        List<Activity> activities = activityRepository.findByUserId(userId);
            return activities.stream()
                    .map(this::mapToResponse)
                    .collect(Collectors.toList());
    }

    public ActivityResponse getActivityById(String activityId) {
        return activityRepository.findById(activityId)
                .map(this::mapToResponse)
                .orElseThrow(() -> new RuntimeException("Activity not found with id: " + activityId));}

}
