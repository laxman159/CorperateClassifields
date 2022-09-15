package com.cognizant.offers.models;

import java.util.Date;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Author : Laxman Sharma
 * Data: 27th JUN 2022
 * A custom responce entity make for post request for error
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ErrorResponse {
    private String message;
    private HttpStatus status;
    private Date timestamp;
}
