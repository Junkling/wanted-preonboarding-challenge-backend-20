package com.example.wanted_6;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class Wanted6Application {

	public static void main(String[] args) {
		SpringApplication.run(Wanted6Application.class, args);
	}

}
