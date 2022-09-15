package com.cts.authmicroservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

import lombok.extern.slf4j.Slf4j;

@SpringBootApplication
@Slf4j
// @EnableSwagger2
@ComponentScan("com.cts.authmicroservice")
@CrossOrigin("http://localhost:3000")
public class AuthmicroserviceApplication {

	public static void main(String[] args) {
		log.info("Inside auth microservice");
		SpringApplication.run(AuthmicroserviceApplication.class, args);
	}

	// swagger configuration
	// @Bean
	// public Docket productApi() {
	// return new Docket(DocumentationType.SWAGGER_2).select()
	// .apis(RequestHandlerSelectors.basePackage("com.cts.authmicroservice")).build();
	// }

}
