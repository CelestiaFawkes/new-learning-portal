package com.raji.learningportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableJpaAuditing
@EnableJpaRepositories
@EnableTransactionManagement
@SpringBootApplication
public class LearningPortalApplication {

	public static void main(String[] args) {
		SpringApplication.run(LearningPortalApplication.class, args);
	}

}
