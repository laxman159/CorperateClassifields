package com.cognizant.offers;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.web.bind.annotation.CrossOrigin;

/*
 * 
 * Author: Laxman Sharma
 * Class : OfferApplication, Main class excution of this microservice starts form here. 
 * Date created: 23-JUN-2022
 * 
 */

@SpringBootApplication
@EnableFeignClients("com.cognizant.offers")
@CrossOrigin("http://localhost:3000")
public class OffersApplication {

	public static void main(String[] args) {
		SpringApplication.run(OffersApplication.class, args);
	}

}
