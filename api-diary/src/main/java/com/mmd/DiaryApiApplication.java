package com.mmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = "com.mmd")
@EnableJpaRepositories(basePackages = "com.mmd.repository")
public class DiaryApiApplication {
	public static void main(String[] args) {
		SpringApplication.run(DiaryApiApplication.class, args);
	}
}
