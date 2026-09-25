package com.fitness.activityservice.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fitness.activityservice.dto.ActivityRequest;
import com.fitness.activityservice.dto.ActivityResponse;
import com.fitness.activityservice.service.ActivityService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.GetMapping;
import java.util.List;
import org.springframework.web.bind.annotation.PathVariable;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;


@RestController 
@RequestMapping("api/activities")
@AllArgsConstructor 
@Slf4j
public class ActivityController {

    private ActivityService activityService;

    @PostMapping 
    public ResponseEntity<ActivityResponse> trackActivity(@RequestBody ActivityRequest request){
        log.info("POST /api/activities received for userId={}, type={}", request.getUserId(), request.getType());
        return ResponseEntity.ok(activityService.trackActivity(request));
    }

    @GetMapping
    public ResponseEntity<List<ActivityResponse>> getUserActivities(@RequestHeader ("X-User-ID") String userId) {
        log.info("GET /api/activities received for userId={}", userId);
        return ResponseEntity.ok(activityService.getUserActivities(userId));
    }
    
    @GetMapping("/{activityId}")
    public ResponseEntity<ActivityResponse> getActivity(@PathVariable String activityId) {
        log.info("GET /api/activities/{} received", activityId);
        return ResponseEntity.ok(activityService.getActivityById(activityId));
    }
}
