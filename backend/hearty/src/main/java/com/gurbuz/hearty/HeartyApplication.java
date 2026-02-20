package com.gurbuz.hearty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.gurbuz.hearty.repository")
public class HeartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeartyApplication.class, args);
	}

}
