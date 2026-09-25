package com.fitness.activityservice.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.beans.factory.annotation.Value;

@Configuration 
public class RabbitMqConfig {

    @Value("${rabbitmq.exchange.name}")
    private String exchange;

    @Value("${rabbitmq.queue.name}")
    private String queue;

    @Value("${rabbitmq.routing.key}")
    private String routingKey;

    @Bean
    public Queue activityQueue() {
        // Create a durable queue named "activity.queue"
        // durable means the queue will persist even if RabbitMQ restarts
        return new Queue(queue, true);
    }
    
    @Bean 
    public DirectExchange activityExchange() {
        // Create a direct exchange named "fitness.exchange"
        return new DirectExchange(exchange);
    }

    @Bean 
    public Binding activityBinding(Queue activityQueue, DirectExchange activityExchange) {
        // Bind the queue to the exchange with the routing key "activity.tracking"
        return BindingBuilder.bind(activityQueue).to(activityExchange).with(routingKey);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        // Automatically convert messages to and from JSON format
        return new JacksonJsonMessageConverter();
    }
}
