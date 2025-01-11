package org.example.testing_hub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;

@SpringBootApplication
@EntityScan(basePackages = "org.example.testing_hub.entity")
public class TestingHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(TestingHubApplication.class, args);
	}

}
