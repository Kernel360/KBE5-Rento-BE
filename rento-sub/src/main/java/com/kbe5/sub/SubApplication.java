package com.kbe5.sub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableRetry
@EnableScheduling
@SpringBootApplication(scanBasePackages = {
        "com.kbe5.common",
        "com.kbe5.domain",
        "com.kbe5.infra",
        "com.kbe5.sub"
})
@EntityScan(basePackages = "com.kbe5.domain")
@EnableJpaRepositories(basePackages = "com.kbe5.domain")
class SubApplication {

    public static void main(String[] args) {
        SpringApplication.run(SubApplication.class, args);
    }

}
