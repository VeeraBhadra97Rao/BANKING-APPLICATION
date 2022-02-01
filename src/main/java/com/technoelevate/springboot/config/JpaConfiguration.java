package com.technoelevate.springboot.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@ComponentScan(basePackages = { "com.technoelevate.springboot" })
@EnableJpaRepositories(basePackages = "com.technoelevate.springboot.repository")
public class JpaConfiguration {

}
