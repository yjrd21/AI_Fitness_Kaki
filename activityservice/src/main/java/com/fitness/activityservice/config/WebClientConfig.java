package com.fitness.activityservice.config;

import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.context.annotation.Bean;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Slf4j
public class WebClientConfig {

    @Bean
    @LoadBalanced // Allows WebClient to resolve service names via Eureka
    public WebClient.Builder webClientBuilder() {
        log.info("Creating load-balanced WebClient builder");
        return WebClient.builder();
    }

    @Bean
    public WebClient userServiceWebClient(WebClient.Builder webClientBuilder) {
        log.info("Creating User Service WebClient");
        return webClientBuilder
                .baseUrl("http://USER-SERVICE")
                .build();
    }
}
