package com.fitness.aiservice.service;
import com.fitness.aiservice.model.Activity;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;
import lombok.RequiredArgsConstructor;

@Service 
@Slf4j 
@RequiredArgsConstructor
public class ActivityMessageListener {

    @RabbitListener(queues = "${rabbitmq.queue.name}")
    public void processActivity(Activity activity) { 
        log.info("Received activity message: {}", activity.getId());
    }

}
