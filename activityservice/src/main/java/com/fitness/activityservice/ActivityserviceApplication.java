package com.fitness.activityservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
public class ActivityserviceApplication {

	public static void main(String[] args) {
		log.info("Starting Activity Service");
		SpringApplication.run(ActivityserviceApplication.class, args);
	}

}
