package com.technoelevate.springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "com.technoelevate.springboot.repository")
public class BankApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankApplication.class, args);
	}

}
