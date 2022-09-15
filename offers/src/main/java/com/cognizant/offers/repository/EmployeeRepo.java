package com.cognizant.offers.repository;

import org.springframework.data.repository.CrudRepository;
import com.cognizant.offers.models.Employee;

/*
 * 
 * Author: Laxman Sharma
 * Class : ExmployeeRepo, ripository container for the database table named Employee 
 * Date created: 23-JUN-2022
 * 
 */
public interface EmployeeRepo extends CrudRepository<Employee, Integer> {

}
