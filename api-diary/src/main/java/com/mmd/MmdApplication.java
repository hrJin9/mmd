package com.mmd;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.mmd")
public class MmdApplication {

	public static void main(String[] args) {
		SpringApplication.run(MmdApplication.class, args);
	}

}
