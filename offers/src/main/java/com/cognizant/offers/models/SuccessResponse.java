package com.cognizant.offers.models;

import java.util.Date;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Author : Laxman Sharma
 * Data: 27th JUN 2022
 * A custom responce entity make for post request for success
 */

@Component
@Data
@NoArgsConstructor
public class SuccessResponse {
    private String message;
    private HttpStatus status;
    private Date timestamp;
}