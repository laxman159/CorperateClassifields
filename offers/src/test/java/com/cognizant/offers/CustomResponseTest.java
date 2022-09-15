package com.cognizant.offers;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;

import com.cognizant.offers.models.AuthorizationResponse;
import com.cognizant.offers.models.ErrorResponse;
import com.cognizant.offers.models.SuccessResponse;

@SpringBootTest
public class CustomResponseTest {

    @InjectMocks
    AuthorizationResponse authorizationResponse;

    @InjectMocks
    ErrorResponse errorResponse;

    @InjectMocks
    SuccessResponse successResponse;

    // Error response
    @Test
    public void testMessage() {
        errorResponse.setMessage("some message");
        assertEquals("some message", errorResponse.getMessage());
    }

    @Test
    public void testStatus() {
        errorResponse.setStatus(HttpStatus.ACCEPTED);
        assertEquals(HttpStatus.ACCEPTED, errorResponse.getStatus());
    }

    @Test
    public void testTimestamp() {
        errorResponse.setTimestamp(new Date());
        assertEquals(errorResponse.getTimestamp(), new Date());
    }

    // Success response
    @Test
    public void testMessageSuccess() {
        successResponse.setMessage("some message");
        assertEquals("some message", successResponse.getMessage());
    }

    @Test
    public void testStatusSuccess() {
        successResponse.setStatus(HttpStatus.ACCEPTED);
        assertEquals(HttpStatus.ACCEPTED, successResponse.getStatus());
    }

    @Test
    public void testTimestampSuccess() {
        successResponse.setTimestamp(new Date());
        assertEquals(successResponse.getTimestamp(), new Date());
    }

    // authresponse test
    @Test
    public void testUsername() {
        authorizationResponse.setUsername("laxman");
        assertEquals("laxman", authorizationResponse.getUsername());
    }

    @Test
    public void testEmpId() {
        authorizationResponse.setEmpid(2);
        assertEquals(2, authorizationResponse.getEmpid());
    }

    @Test
    public void testValid() {
        authorizationResponse.setValid(true);
        assertEquals(true, authorizationResponse.isValid());
    }
}
