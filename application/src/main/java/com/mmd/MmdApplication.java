package com.mmd;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@Slf4j
@SpringBootApplication(scanBasePackages = "com.mmd")
public class MmdApplication {

	public static void main(String[] args) {
//		System.setProperty("spring.config.name", "application-api-diary,application-auth,application-auth-oauth2,application-aws,application-core-mysql,application-core-redis");
		SpringApplication.run(MmdApplication.class, args);
	}

}
