package com.cognizant.offers.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import com.cognizant.offers.models.AuthorizationResponse;

/*
 * Author: Laxman Sharma
 * CLass : AuthorizationClient - for connecting to   auhentication microservice
 * Date: 24-JUN-2022
 *
 */

@FeignClient(url = "${auth.feign.client}", name = "${auth.feign.name}")
public interface AuthorizationClient {

    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/validate")
    public ResponseEntity<AuthorizationResponse> verifyJwtToken(
            @RequestHeader(name = "Authorization", required = true) String token);
}
