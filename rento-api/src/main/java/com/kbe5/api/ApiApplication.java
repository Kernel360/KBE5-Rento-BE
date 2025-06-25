package com.kbe5.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication(scanBasePackages = {
        "com.kbe5.common",
        "com.kbe5.domain",
        "com.kbe5.infra",
        "com.kbe5.api"
})
@EnableJpaRepositories(basePackages = "com.kbe5.domain")
@EntityScan(basePackages = "com.kbe5.domain")
public class ApiApplication {
    public static void main(String[] args) {
        SpringApplication.run(ApiApplication.class, args);
    }
}
