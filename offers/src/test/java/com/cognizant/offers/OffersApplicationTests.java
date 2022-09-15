package com.cognizant.offers;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.offers.models.Employee;
import com.cognizant.offers.models.Offer;
import com.cognizant.offers.repository.EmployeeRepo;
import com.cognizant.offers.repository.OfferRepo;

@SpringBootTest
class OffersApplicationTests {

	@Autowired
	EmployeeRepo employeeRepo;

	@Autowired
	OfferRepo offerRepo;

	@Test
	void contextLoads() {
	}

	// @Test
	// void testEmployee() {
	// Employee employee = employeeRepo.findById(1).get();
	// System.out.println("Employee Name: " + employee.getName());
	// }

	// @Test
	// void testOffer() {
	// Offer offer = offerRepo.findById(4).get();
	// System.out.println(offer.getName());
	// }

}
