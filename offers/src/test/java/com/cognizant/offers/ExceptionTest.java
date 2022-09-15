package com.cognizant.offers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.cognizant.offers.exceptions.AllExceptionHandler;
import com.cognizant.offers.exceptions.DateNotFoundException;
import com.cognizant.offers.exceptions.InvalidEmployeeException;
import com.cognizant.offers.exceptions.InvalidTokenException;
import com.cognizant.offers.exceptions.OfferAlreadyTakenException;
import com.cognizant.offers.exceptions.OfferNotFoundException;

/*
 * Autor: Laxman Sharma
 * Date: 27th JUN 2022
 * Junit test for  all the exception class
 */

public class ExceptionTest {

    AllExceptionHandler allExceptionHandler = new AllExceptionHandler();

    @Test
    public void testOfferNotFoundException() {
        assertEquals(HttpStatus.NOT_FOUND,
                allExceptionHandler.offerNotFound(new OfferNotFoundException(null)).getStatusCode());
    }

    @Test
    public void testEmployeeNotFoundException() {
        assertEquals(HttpStatus.NOT_FOUND,
                allExceptionHandler.employeeNotFoundException(new InvalidEmployeeException(null)).getStatusCode());
    }

    @Test
    public void testInvalidTokenException() {
        assertEquals(HttpStatus.UNAUTHORIZED,
                allExceptionHandler.invalidTokenException(new InvalidTokenException(null)).getStatusCode());
    }

    @Test
    public void testInvalidDateException() {
        assertEquals(HttpStatus.BAD_REQUEST,
                allExceptionHandler.invalidDateException(new DateNotFoundException(null)).getStatusCode());
    }

    @Test
    public void testOfferAlreadyTakenException() {
        assertEquals(HttpStatus.UNAUTHORIZED,
                allExceptionHandler.offerEngagedException(new OfferAlreadyTakenException(null)).getStatusCode());
    }
}
