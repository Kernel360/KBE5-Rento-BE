package com.kbe5.rento;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.retry.annotation.EnableRetry;

@EnableRetry
@SpringBootApplication
public class RentoApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentoApplication.class, args);
	}

}
