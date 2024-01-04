package com.todos.mmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class MmdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmdApplication.class, args);
	}

}
