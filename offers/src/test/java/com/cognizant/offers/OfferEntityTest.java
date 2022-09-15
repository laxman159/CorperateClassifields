package com.cognizant.offers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;
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
 * Junit test for  the Getter and Setter methods for the Offer model
 */

@SpringBootTest
public class OfferEntityTest {

    @InjectMocks
    Offer offer;

    @InjectMocks
    Employee employee;

    @Test
    public void testNameGetterSetter() {
        offer.setName("Bike for sale");
        assertEquals("Bike for sale", offer.getName());
    }

    @Test
    public void testCategoryGetterSetter() {
        offer.setCategory("Transport");
        assertEquals("Transport", offer.getCategory());
    }

    @Test
    public void testOpenDateGetterSetter() {
        Date date = new Date();
        offer.setOpenDate(date);
        assertEquals(offer.getOpenDate(), date);
    }

    @Test
    public void testDescriptionGetterSetter() {
        offer.setDescription("Lorem ipsum");
        assertEquals("Lorem ipsum", offer.getDescription());
    }

    @Test
    public void testEngagedDateGetterSetter() {
        Date date = new Date();
        offer.setEngagedDate(date);
        assertEquals(offer.getEngagedDate(), date);
    }

    @Test
    public void testEmpGetterSetter() {
        offer.setEmp(employee);
        assertEquals(offer.getEmp(), employee);
    }

    @Test
    public void testClosedDateGetterSetter() {
        Date date = new Date();
        offer.setClosedDate(date);
        assertEquals(offer.getClosedDate(), date);
    }

    @Test
    public void testEngagedEmpGetterSetter() {
        offer.setEngagedEmp(employee);
        assertEquals(offer.getEngagedEmp(), employee);
    }

    @Test
    public void testLikedByEmployeesGetterSetter() {
        Set<Employee> emps = new HashSet<Employee>();
        emps.add(employee);
        offer.setLikedByEmployees(emps);
        assertEquals(offer.getLikedByEmployees(), emps);
    }

}
