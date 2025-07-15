package com.kbe5.infra.rabbitmq.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Slf4j
@EnableRabbit
@Configuration
public class ProducerRabbitConfig {

    //큐 정의
    @Bean
    public Queue cycleInfo1() {
        return new Queue("cycle-info-1");
    }
    //큐 정의
    @Bean
    public Queue cycleInfo2() {
        return new Queue("cycle-info-2");
    }
    //큐 정의
    @Bean
    public Queue cycleInfo3() {
        return new Queue("cycle-info-3");
    }

    //알림 큐 정의
    @Bean
    public Queue notification() {
        return new Queue("notification");
    }

    @Bean
    public Queue streamInfo() { return new Queue("cycle-info-stream"); }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        return rabbitTemplate;
    }
}
