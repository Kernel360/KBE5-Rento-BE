package com.kbe5.adapter;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.kbe5.common",
        "com.kbe5.domain",
        "com.kbe5.infra",
        "com.kbe5.adapter"
}
)
@EnableJpaRepositories(basePackages = "com.kbe5.domain")
@EntityScan(basePackages = "com.kbe5.domain")
@EnableRabbit
class AdapterApplication {

    public static void main(String[] args) {
        SpringApplication.run(AdapterApplication.class, args);
    }

}
