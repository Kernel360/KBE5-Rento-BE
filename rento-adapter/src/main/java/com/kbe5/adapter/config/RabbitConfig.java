package com.kbe5.adapter.config;

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
public class RabbitConfig {

    //큐 정의
    @Bean
    public Queue cycleInfo() {
        return new Queue("cycle-info");
    }

    //알림 큐 정의
    @Bean
    public Queue notification() {
        return new Queue("notification");
    }

    @Bean
    public MessageConverter converter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public AmqpTemplate amqpTemplate(CachingConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(converter());

        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (ack) {
                log.info("✅ RabbitMQ 브로커가 메시지를 정상적으로 받았습니다. {}", correlationData);
            } else {
                log.error("🚨 RabbitMQ 브로커가 메시지를 받지 못했습니다. 원인: {}", cause);
                // 여기서 슬랙 알림, DB 로깅, 재시도 등 가능
            }
        });

        return rabbitTemplate;
    }
}
