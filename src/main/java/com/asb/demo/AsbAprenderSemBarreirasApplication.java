package com.asb.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"com.asb.model.entity"})
@EnableJpaRepositories(basePackages = {"com.asb.repositories"})
@ComponentScan(basePackages = {"com.asb.controller", "com.asb.service", "com.asb.serviceImpl" })
@SpringBootApplication
public class AsbAprenderSemBarreirasApplication {

	public static void main(String[] args) {
		SpringApplication.run(AsbAprenderSemBarreirasApplication.class, args);
	}

}
