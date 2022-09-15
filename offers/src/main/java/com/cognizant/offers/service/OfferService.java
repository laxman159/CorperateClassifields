package com.cognizant.offers.service;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.cognizant.offers.clients.AuthorizationClient;
import com.cognizant.offers.clients.EmplloyeeClient;
import com.cognizant.offers.exceptions.DateNotFoundException;
import com.cognizant.offers.exceptions.InvalidEmployeeException;
import com.cognizant.offers.exceptions.InvalidTokenException;
import com.cognizant.offers.exceptions.OfferAlreadyTakenException;
import com.cognizant.offers.exceptions.OfferNotFoundException;
import com.cognizant.offers.models.AuthorizationResponse;
import com.cognizant.offers.models.Employee;
import com.cognizant.offers.models.Offer;
import com.cognizant.offers.models.SuccessResponse;
import com.cognizant.offers.repository.OfferRepo;

import lombok.extern.slf4j.Slf4j;

/*
 * 
 * Author: Laxman Sharma
 * Class : OfferService, Service class use by the controller class. 
 * Date created: 23-JUN-2022
 * 
 */

@Slf4j
@Service
public class OfferService {

    @Autowired
    private OfferRepo offerRepo;

    @Autowired
    private EmplloyeeClient emplloyeeClient;

    @Autowired
    private SuccessResponse successResponse;

    @Autowired
    private AuthorizationClient authorizationClient;

    ResponseEntity<AuthorizationResponse> response;

    public Iterable<Offer> getAllOffers(String token) throws InvalidTokenException {

        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.error("error in microservice " + e.getMessage());
        }

        if (response.getBody().isValid()) {

            Iterable<Offer> allOffers = offerRepo.findAll();
            return allOffers;
        } else {
            throw new InvalidTokenException("Token is invalid");
        }

    }

    public Offer getOfferDetils(String token, int offerId) throws OfferNotFoundException, InvalidTokenException {

        // ResponseEntity<AuthorizationResponse> response = null;

        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("Error in authorization microservice");
        }

        if (response.getBody().isValid()) {
            Optional<Offer> offerDetails = offerRepo.findById(offerId);
            if (!offerDetails.isPresent()) {
                throw new OfferNotFoundException("No offers found");
            }
            return offerDetails.get();
        } else {
            throw new InvalidTokenException("Token is invalid, Please login again");
        }

    }

    public List<Offer> getOfferByCategory(String token, String category)
            throws OfferNotFoundException, InvalidTokenException {

        try {
            response = authorizationClient.verifyJwtToken(token);

        } catch (Exception e) {
            log.info("Error in authorization microservice");
        }
        if (response.getBody().isValid()) {
            List<Offer> offerDetails = offerRepo.getOfferByCategory(category);
            if (offerDetails.isEmpty()) {
                throw new OfferNotFoundException("No offer of category " + category + " found");
            }
            return offerDetails;
        } else {
            throw new InvalidTokenException("Token is not valid");
        }

    }

    public List<Offer> getOfferByTopLikes(String token) throws OfferNotFoundException, InvalidTokenException {
        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("Error in authorization microservice");
        }

        if (response.getBody().isValid()) {
            List<Offer> offers = offerRepo.findAll(PageRequest.of(0, 3, Sort.by("likes").descending()));
            if (offers.isEmpty()) {
                throw new OfferNotFoundException("Top 3 offers not found");
            }
            return offers;
        } else {
            throw new InvalidTokenException("Token is not valid");
        }
    }

    public List<Offer> getOfferByPostDate(String token, String date)
            throws OfferNotFoundException, DateNotFoundException, InvalidTokenException {

        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("Error in authorization microservice");
        }

        if (response.getBody().isValid()) {
            LocalDate currentDate = null;

            try {
                currentDate = LocalDate.parse(date);
            } catch (Exception e) {
                throw new DateNotFoundException("Enter a valid date");
            }
            currentDate = LocalDate.parse(date);

            int month = currentDate.getMonthValue();
            int year = currentDate.getYear();
            int day = currentDate.getDayOfMonth();

            List<Offer> offers = offerRepo.getOfferByPostedDate(day, month, year);
            if (offers.isEmpty()) {
                throw new OfferNotFoundException(
                        "Offer with the given date " + day + " " + month + " " + year + " not found");
            }
            return offers;
        } else {
            throw new InvalidTokenException("Token is not valid");
        }
    }

    public SuccessResponse editOffer(String token, Offer offerDetails)
            throws OfferNotFoundException, InvalidTokenException, InvalidEmployeeException {

        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("Error in authorization microservice");
        }

        if (response.getBody().isValid()) {
            Optional<Offer> offer = offerRepo.findById(offerDetails.getId());

            if (!offer.isPresent()) {
                throw new OfferNotFoundException("Offer not found");
            }

            if (offer.get().getEmp().getId() != response.getBody().getEmpid()) {
                throw new InvalidEmployeeException(
                        "You cannot edit this offer. You can only edit the offers that you created");
            }

            Offer offerEdited = offer.get();
            offerEdited.setCategory(offerDetails.getCategory());
            offerEdited.setDescription(offerDetails.getDescription());
            offerEdited.setName(offerDetails.getName());
            offerEdited.setLikes(offerDetails.getLikes());

            offerRepo.save(offerEdited);

            successResponse.setMessage("Offer updated successfully");
            successResponse.setStatus(HttpStatus.OK);
            successResponse.setTimestamp(new Date());

            return successResponse;
        } else {
            throw new InvalidTokenException("Token is not valid");
        }

    }

    public SuccessResponse addOffer(String token, Offer offerDetails)
            throws InvalidEmployeeException, InvalidTokenException {

        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("some error in auth microservice");

        }

        if (response.getBody().isValid()) {

            int employeeId = response.getBody().getEmpid();
            Employee employee = null;

            try {
                employee = emplloyeeClient.getEmployee(token, employeeId);
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            offerDetails.setEmp(employee);
            offerDetails.setOpenDate(new Date());
            offerDetails.setClosedDate(null);
            offerDetails.setEngagedDate(null);
            offerRepo.save(offerDetails);

            successResponse.setMessage("Offer added successfully");
            successResponse.setStatus(HttpStatus.ACCEPTED);
            successResponse.setTimestamp(new Date());

            return successResponse;
        } else {
            throw new InvalidTokenException("Token is not valid");
        }

    }

    public List<Offer> getOfferById(String token, int id)
            throws OfferNotFoundException, InvalidEmployeeException, InvalidTokenException {

        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("some error in auth microservice");
        }

        if (response.getBody().isValid()) {
            if (response.getBody().getEmpid() != id) {
                throw new InvalidTokenException("Token is invalid");
            }

            List<Offer> offers = offerRepo.findAllByEmployeeId(id);

            if (offers.isEmpty()) {
                throw new OfferNotFoundException("No offers found");
            }
            return offers;
        } else {
            throw new InvalidTokenException("Token is invalid");
        }

    }

    public SuccessResponse engageOffer(String token, int offerId, int employeeId)
            throws InvalidTokenException, InvalidEmployeeException, OfferNotFoundException, OfferAlreadyTakenException {

        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.error("Error in microservice " + e.getMessage());
        }
        // verify if token is valid
        if (response.getBody().isValid()) {
            // check if the id is not the same as the logged in user
            if (response.getBody().getEmpid() != employeeId) {
                successResponse.setMessage("User  is invalid");
                successResponse.setStatus(HttpStatus.UNAUTHORIZED);
                successResponse.setTimestamp(new Date());
                return successResponse;
            }

            // checking if the offer exists
            Optional<Offer> offer = offerRepo.findById(offerId);
            if (!offer.isPresent()) {
                throw new OfferNotFoundException("Offer does not exists");
            }
            // check if the offer is already closed
            if (offer.get().getClosedDate() != null) {
                successResponse.setMessage("Offer is already closed");
                successResponse.setStatus(HttpStatus.BAD_REQUEST);
                successResponse.setTimestamp(new Date());
                return successResponse;
            }

            // check if the offer is already engaged
            else if (offer.get().getEngagedDate() != null) {
                throw new OfferAlreadyTakenException("Offer is being engaged");
            } else {
                // the offer is not being engaged
                Employee employee = null;
                try {
                    employee = emplloyeeClient.getEmployee(token, employeeId);
                } catch (Exception e) {
                    log.error("Error in microservice " + e.getMessage());

                }
                // verify if the offer belongs to the same employee
                if (offer.get().getEmp().getId() == employeeId) {
                    successResponse.setMessage("Employee cannot be engaged with his own offer");
                    successResponse.setStatus(HttpStatus.BAD_REQUEST);
                    successResponse.setTimestamp(new Date());
                    return successResponse;
                }

                Offer offerFinal = offer.get();
                offerFinal.setEngagedEmp(employee);
                offerFinal.setEngagedDate(new Date());
                offerRepo.save(offerFinal);
                successResponse.setMessage("Engaged in the offer successfully");
                successResponse.setStatus(HttpStatus.CREATED);
                successResponse.setTimestamp(new Date());
                return successResponse;
            }
        } else {
            throw new InvalidTokenException("Token is invalid");
        }
    }

    public int getPointsById(String token, int emp_id) throws InvalidTokenException, InvalidEmployeeException {
        // ResponseEntity<AuthResponse> response;

        // authenticate the user
        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("some error in auth microservice");

        }

        // check if token is valid
        if (response.getBody().isValid()) {

            // verify the user
            if (response.getBody().getEmpid() != emp_id) {
                throw new InvalidTokenException("token is invalid for user");
            }
            // return user points
            Employee emp;
            try {
                emp = emplloyeeClient.getEmployee(token, emp_id);
            } catch (Exception e) {
                throw new InvalidEmployeeException("The given employee was not found " + e.getMessage());
            }

            return emp.getPointsGained();
        } else {
            throw new InvalidTokenException("token is invalid or expired");
        }
    }

    public SuccessResponse updateLikes(String token, int id) throws OfferNotFoundException, InvalidTokenException {
        // ResponseEntity<AuthResponse> response;
        // authenticate the user
        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("some error in auth microservice");

        }

        // check if token is valid
        if (response.getBody().isValid()) {
            Offer offer = offerRepo.findById(id).orElse(null);

            // check if the offer is available
            if (offer == null) {
                throw new OfferNotFoundException("offer not found");
            }

            // update the likes
            int likes = offer.getLikedByEmployees().size();
            log.info("" + offer);
            offer.setLikes(likes);

            // save in the repository
            offerRepo.save(offer);

            successResponse.setMessage("likes updated successfully");
            successResponse.setStatus(HttpStatus.OK);
            successResponse.setTimestamp(new Date());
            return successResponse;
        } else {
            throw new InvalidTokenException("invalid user");
        }
    }

    public List<String> getDistinctCategory(String token) throws InvalidTokenException {

        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.error("Error in microservice");
        }

        if (response.getBody().isValid()) {
            return offerRepo.getAllDistinctCategory();
        } else {
            throw new InvalidTokenException("Token is expired, Please log in again");
        }
    }

    public List<Offer> getOffersById(String token, int emp_id)
            throws OfferNotFoundException, InvalidTokenException {
        // ResponseEntity<AuthorizationResponse> response;

        // authenticate the user
        try {
            response = authorizationClient.verifyJwtToken(token);
        } catch (Exception e) {
            log.info("some error in auth microservice");

        }

        // check if token is valid
        if (response.getBody().isValid()) {

            // verify the employee
            if (response.getBody().getEmpid() != emp_id) {
                throw new InvalidTokenException("token is invalid for the current user");
            }

            // get the offer details for the employee
            List<Offer> offers = offerRepo.findAllByEmployeeId(emp_id);

            if (offers.size() == 0) {
                throw new OfferNotFoundException("no offers found");
            }
            return offers;
        } else {
            log.info("expired or invalid token");
            throw new InvalidTokenException("token is invalid");
        }
    }

}
