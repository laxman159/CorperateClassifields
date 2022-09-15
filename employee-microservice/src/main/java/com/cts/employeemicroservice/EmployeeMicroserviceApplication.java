package com.cts.employeemicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import org.springframework.context.annotation.ComponentScan;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@SpringBootApplication

@EnableFeignClients
@ComponentScan("com.cts.employeemicroservice")
public class EmployeeMicroserviceApplication {

	public static void main(String[] args) {
		log.info("Inside EmployeeMicroService");
		SpringApplication.run(EmployeeMicroserviceApplication.class, args);
	}

	// swagger configuration
	// @Bean
	// public Docket productApi() {
	// return new Docket(DocumentationType.SWAGGER_2).select()
	// .apis(RequestHandlerSelectors.basePackage("com.cts.employeemicroservice")).build();
	// }

}