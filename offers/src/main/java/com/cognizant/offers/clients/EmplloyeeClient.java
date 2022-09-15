package com.cognizant.offers.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import com.cognizant.offers.models.Employee;

/*
 * Author: Laxman Sharma
 * CLass : EmplloyeeClient - for connecting to   employee microservice
 * Date: 25-JUN-2022
 *
 */

@FeignClient(url = "${employee.feign.client}", name = "${employee.feign.name}")
public interface EmplloyeeClient {

    @GetMapping("/viewProfile/{id}")
    public Employee getEmployee(@RequestHeader(name = "Authorization", required = true) String token,
            @PathVariable("id") int id);

}
