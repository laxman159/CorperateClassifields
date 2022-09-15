package com.cognizant.offers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;

import com.cognizant.offers.models.Employee;
import com.cognizant.offers.models.Offer;

/*
 * Autor: Laxman Sharma
 * Date: 27th JUN 2022
 * Junit test for  the Getter and Setter methods for the Employee model
 */

@SpringBootTest
public class EmployeeModelTest {

    @InjectMocks
    Employee employee;

    @InjectMocks
    Offer offer;

    @Test
    public void testNameGettSett() {
        employee.setName("Laxman");
        assertEquals("Laxman", employee.getName());
    }

    @Test
    public void testDepartmentGettSett() {
        employee.setDepartment("AWS");
        assertEquals("AWS", employee.getDepartment());
    }

    @Test
    public void testGenderGettSett() {
        employee.setGender("male");
        assertEquals("male", employee.getGender());
    }

    @Test
    public void testAgeDateGettSett() {
        employee.setAge(23);
        assertEquals(23, employee.getAge());
    }

    @Test
    public void testContactNumberGettSett() {
        long number = Long.parseLong(new String("123456789"));
        employee.setContactNumber(number);
        assertEquals(employee.getContactNumber(), number);
    }

    @Test
    public void testEmailDateGettSett() {
        employee.setEmail("laxman@gmail.com");
        assertEquals("laxman@gmail.com", employee.getEmail());
    }

    @Test
    public void testPointsGainedGettSett() {
        employee.setPointsGained(5);
        assertEquals(5, employee.getPointsGained());
    }

    @Test
    public void testOffersGettSett() {
        Set<Offer> offers = new HashSet<Offer>();
        offers.add(offer);
        employee.setOffers(offers);
        assertEquals(employee.getOffers(), offers);
    }

    @Test
    public void testEnagedInOffersGettSett() {
        Set<Offer> offers = new HashSet<Offer>();
        offers.add(offer);
        employee.setEngagedInOffers(offers);
        assertEquals(employee.getEngagedInOffers(), offers);
    }

    @Test
    public void testLikedOffersGettSett() {
        Set<Offer> offers = new HashSet<Offer>();
        offers.add(offer);
        employee.setLikedOffers(offers);
        assertEquals(employee.getLikedOffers(), offers);
    }

}
