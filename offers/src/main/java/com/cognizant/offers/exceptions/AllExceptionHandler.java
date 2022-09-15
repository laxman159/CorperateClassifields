package com.cognizant.offers.exceptions;

import java.util.Date;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.cognizant.offers.models.ErrorResponse;
import com.cognizant.offers.models.Offer;

/*
 * Author: Laxman Sharma
 * Data : 27th JUN 2022
 * A convenient base class for @ControllerAdvice classes that wish to provide centralized exception handling across all @RequestMapping methods through @ExceptionHandler methods
 * 
 */

@ControllerAdvice
public class AllExceptionHandler extends ResponseEntityExceptionHandler {
    ErrorResponse errorResponse = new ErrorResponse();

    @ExceptionHandler(OfferNotFoundException.class)
    public ResponseEntity<ErrorResponse> offerNotFound(OfferNotFoundException offerNotFoundException) {

        errorResponse.setMessage(offerNotFoundException.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setTimestamp(new Date());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(InvalidEmployeeException.class)
    public ResponseEntity<ErrorResponse> employeeNotFoundException(InvalidEmployeeException invalidEmployeeException) {

        errorResponse.setMessage(invalidEmployeeException.getMessage());
        errorResponse.setStatus(HttpStatus.NOT_FOUND);
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);

    }

    @ExceptionHandler(InvalidTokenException.class)
    public ResponseEntity<ErrorResponse> invalidTokenException(InvalidTokenException invalidTokenException) {

        errorResponse.setMessage(invalidTokenException.getMessage());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(OfferAlreadyTakenException.class)
    public ResponseEntity<ErrorResponse> offerEngagedException(OfferAlreadyTakenException offerAlreadyTakenException) {

        errorResponse.setMessage(offerAlreadyTakenException.getMessage());
        errorResponse.setStatus(HttpStatus.UNAUTHORIZED);
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<>(errorResponse, HttpStatus.UNAUTHORIZED);

    }

    @ExceptionHandler(DateNotFoundException.class)
    public ResponseEntity<ErrorResponse> invalidDateException(DateNotFoundException dateNotFoundException) {

        errorResponse.setMessage(dateNotFoundException.getMessage());
        errorResponse.setStatus(HttpStatus.BAD_REQUEST);
        errorResponse.setTimestamp(new Date());

        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);

    }

}
