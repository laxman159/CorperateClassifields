
package com.cognizant.offers.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Access;
import javax.persistence.AccessType;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cognizant.offers.exceptions.DateNotFoundException;
import com.cognizant.offers.exceptions.InvalidEmployeeException;
import com.cognizant.offers.exceptions.InvalidTokenException;
import com.cognizant.offers.exceptions.OfferAlreadyTakenException;
import com.cognizant.offers.exceptions.OfferNotFoundException;
import com.cognizant.offers.models.Offer;
import com.cognizant.offers.models.SuccessResponse;
import com.cognizant.offers.service.OfferService;

import lombok.extern.slf4j.Slf4j;

/*
 *
 * Author: Laxman Shara
 * Class : OfferController, controller for the rest end points
 * Date created: 24-JUN-2022
 * 
 */

@Slf4j
@RestController
@RequestMapping("/offers")
public class OfferController {

    @Autowired
    OfferService offerService;

    // @CrossOrigin(origins = "http://localhost:3000")
    // @GetMapping("/getAllOffers")
    // public Iterable<Offer> getAllOffers(@RequestHeader("Authorization") String
    // token) throws InvalidTokenException {
    // log.debug("inside the getAllOffers() controller");
    // return offerService.getAllOffers(token);
    // }

    // Get the offer details of an offer
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getOfferDetails/{id}")
    public Offer getOfferDetails(@RequestHeader("Authorization") String token,
            @PathVariable("id") int offerId)
            throws OfferNotFoundException, InvalidTokenException {
        log.debug("inside getOfferDetails() controller");
        return offerService.getOfferDetils(token, offerId);
    }

    // @CrossOrigin(origins = "http://localhost:3000")
    // @GetMapping("/getOfferByCategory/{category}")
    // public List<Offer> getOfferByCategory(@RequestHeader("Authorization") String
    // token,
    // @PathVariable("category") String category) throws OfferNotFoundException,
    // InvalidTokenException {
    // log.debug("inside getOfferByCategory() controller");
    // return offerService.getOfferByCategory(token, category);
    // }

    // @CrossOrigin(origins = "http://localhost:3000")
    // @GetMapping("/getOfferByLikes")
    // public Iterable<Offer> getOfferByLikes(String token) throws
    // OfferNotFoundException, InvalidTokenException {
    // log.debug("inside getOfferByLikes() controller");
    // return offerService.getOfferByTopLikes(token);
    // }

    // Add an offer to the db
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/addOffer")
    public SuccessResponse addOffer(@RequestHeader("Authorization") String token, @RequestBody Offer offerDetails)
            throws InvalidTokenException, InvalidEmployeeException {
        log.debug("inside addOffer() controller");
        return offerService.addOffer(token, offerDetails);
    }

    // edit an offer
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/editOffer")
    public SuccessResponse updateOffer(@RequestHeader("Authorization") String token, @RequestBody Offer offerDetails)
            throws OfferNotFoundException, InvalidEmployeeException, InvalidTokenException {
        log.debug("inside updateOffer() controller");
        return offerService.editOffer(token, offerDetails);
    }

    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/engageOffer")
    public SuccessResponse engageOffer(@RequestHeader("Authorization") String token,
            @RequestParam("offerId") int offerId, @RequestParam("employeeId") int employeeId)
            throws OfferNotFoundException, InvalidEmployeeException, OfferAlreadyTakenException, InvalidTokenException {
        log.debug("inside engageOffer() controller");

        return offerService.engageOffer(token, offerId, employeeId);

    }

    // get the points gained from the employee
    @GetMapping("/getPoints/{emp_id}")
    public int getPointsById(@RequestHeader("Authorization") String token, @PathVariable("emp_id") int emp_id)
            throws OfferNotFoundException, InvalidTokenException, InvalidEmployeeException {
        log.debug("inside getPointsById method of offer microservice");
        return offerService.getPointsById(token, emp_id);

    }

    // refresh the likes count of an offer
    @CrossOrigin(origins = "http://localhost:3000")
    @PostMapping("/updateLikes/{offer_id}")
    public SuccessResponse updateLikes(@RequestHeader("Authorization") String token, @PathVariable("offer_id") int id)
            throws InvalidTokenException, OfferNotFoundException {
        log.debug("inside update likes method of offer microservice");
        return offerService.updateLikes(token, id);
    }

    // get offer based on certian condition
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getOffers")
    public List<Offer> getOffer(@RequestHeader("Authorization") String token,
            @RequestParam(defaultValue = "empty", name = "category") String category,
            @RequestParam(defaultValue = "false", name = "likes") boolean likes,
            @RequestParam(defaultValue = "0", name = "id") int id,
            @RequestParam(defaultValue = "empty", name = "date") String date)
            throws OfferNotFoundException, InvalidTokenException, DateNotFoundException {

        if (!category.equals("empty")) {
            return offerService.getOfferByCategory(token, category);

        } else if (likes) {
            return offerService.getOfferByTopLikes(token);

        } else if (!String.valueOf(id).equals("0")) {
            List<Offer> offer = new ArrayList<>();

            Offer offer2 = offerService.getOfferDetils(token, id);
            offer.add(offer2);
            return offer;
        } else if (!date.equals("empty")) {
            return offerService.getOfferByPostDate(token, date);
        }

        else {
            Iterable<Offer> ofIterable = offerService.getAllOffers(token);
            List<Offer> result = new ArrayList<>();
            ofIterable.forEach(result::add);
            return result;

        }

    }
    // @CrossOrigin(origins = "http://localhost:3000")
    // @GetMapping("/getOfferByPostedDate/{date}")
    // public List<Offer> getOfferByPostedDate(@RequestHeader("Authorization")
    // String token,
    // @PathVariable("date") String postedDate)
    // throws OfferNotFoundException, DateNotFoundException, InvalidTokenException {
    // log.debug("inside getOfferByPostedDate method of offer microservice");
    // List<Offer> offers = offerService.getOfferByPostDate(token, postedDate);
    // return offers;
    // }

    // get the distinct category from the employee table
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getDistinctCategory")
    public List<String> getDistinctCategory(@RequestHeader("Authorization") String token) throws InvalidTokenException {

        return offerService.getDistinctCategory(token);

    }

    // get all the offer of the userr
    @CrossOrigin(origins = "http://localhost:3000")
    @GetMapping("/getOffersById/{emp_id}")
    public List<Offer> getOffersById(@RequestHeader("Authorization") String token, @PathVariable("emp_id") int emp_id)
            throws OfferNotFoundException, InvalidTokenException {
        log.debug("inside getOffersById method of offer microservice");
        return offerService.getOffersById(token, emp_id);

    }

}
