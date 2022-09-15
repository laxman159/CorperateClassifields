package com.cognizant.offers.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * 
 * Author: Laxman Sharma
 * Class : AuthorizationResponse, entity object of response
 * Date created: 23-JUN-2022
 * 
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthorizationResponse {

    private int empid;
    private String username;
    private boolean isValid;
}
