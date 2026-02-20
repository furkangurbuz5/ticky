package com.gurbuz.hearty;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.gurbuz.hearty.config.RsaKeyProperties;

@SpringBootApplication
@EnableJpaRepositories(basePackages = "com.gurbuz.hearty.repository")
@EnableConfigurationProperties(RsaKeyProperties.class)
public class HeartyApplication {

	public static void main(String[] args) {
		SpringApplication.run(HeartyApplication.class, args);
	}

}
