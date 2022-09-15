package com.cognizant.offers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import com.cognizant.offers.clients.AuthorizationClient;
import com.cognizant.offers.clients.EmplloyeeClient;
import com.cognizant.offers.exceptions.DateNotFoundException;
import com.cognizant.offers.exceptions.InvalidEmployeeException;
import com.cognizant.offers.exceptions.InvalidTokenException;
import com.cognizant.offers.exceptions.OfferNotFoundException;
import com.cognizant.offers.models.AuthorizationResponse;
import com.cognizant.offers.models.Employee;
import com.cognizant.offers.models.Offer;
import com.cognizant.offers.models.SuccessResponse;
import com.cognizant.offers.repository.OfferRepo;
import com.cognizant.offers.service.OfferService;

@SpringBootTest
public class OfferServiceTest {

    @InjectMocks
    OfferService offerService;

    @Mock
    AuthorizationClient authClient;

    @Mock
    EmplloyeeClient empClient;

    @Mock
    OfferRepo offerRepository;

    public static AuthorizationResponse authResponse;
    public static SuccessResponse successResponse;

    static {
        authResponse = new AuthorizationResponse();
        authResponse.setEmpid(1);
        authResponse.setUsername("laxman");
        authResponse.setValid(true);

        SuccessResponse successResponse = new SuccessResponse();
        successResponse.setMessage("engaged in the offer successfully");
        successResponse.setStatus(HttpStatus.CREATED);
        successResponse.setTimestamp(new Date());

    }

    @Test
    public void getOfferDetailsTest() throws InvalidTokenException, OfferNotFoundException {

        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authResponse,
                HttpStatus.OK);
        Optional<Offer> offer = Optional.ofNullable(new Offer());

        when(authClient.verifyJwtToken("token")).thenReturn(response);
        when(offerRepository.findById(1)).thenReturn(offer);

        Offer resultOffer = offerService.getOfferDetils("token", 1);
        assertEquals(resultOffer, offer.get());

    }

    @Test
    public void getOfferByCategoryTest() throws InvalidTokenException, OfferNotFoundException {
        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authResponse,
                HttpStatus.OK);
        List<Offer> offers = new ArrayList<Offer>();
        offers.add(new Offer());

        when(authClient.verifyJwtToken("token")).thenReturn(response);
        when(offerRepository.getOfferByCategory("electronics")).thenReturn(offers);

        List<Offer> resultOffers = offerService.getOfferByCategory("token", "electronics");
        assertEquals(resultOffers, offers);
    }

    @Test
    public void getOfferByTopLikesTest() throws InvalidTokenException, OfferNotFoundException {
        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authResponse,
                HttpStatus.OK);
        List<Offer> offers = new ArrayList<Offer>();
        offers.add(new Offer());

        when(authClient.verifyJwtToken("token")).thenReturn(response);
        when(offerRepository.findAll(PageRequest.of(0, 3, Sort.by("likes").descending()))).thenReturn(offers);

        List<Offer> resultOffers = offerService.getOfferByTopLikes("token");

        assertEquals(offers, resultOffers);
    }

    // @Test
    // public void getOfferByPostedDateTest()
    // throws InvalidTokenException, OfferNotFoundException, DateNotFoundException {
    // ResponseEntity<AuthorizationResponse> response = new
    // ResponseEntity<AuthorizationResponse>(authResponse,
    // HttpStatus.OK);

    // List<Offer> offers = new ArrayList<Offer>();
    // offers.add(new Offer());

    // String date = "2022-06-25";
    // LocalDate currentDate = LocalDate.parse(date);
    // int month = currentDate.getMonthValue();
    // int year = currentDate.getYear();
    // int day = currentDate.getDayOfMonth();

    // when(authClient.verifyJwtToken("token")).thenReturn(response);
    // when(offerRepository.getOfferByPostedDate(month, year,
    // day)).thenReturn(offers);

    // List<Offer> resultOffers = offerService.getOfferByPostDate("token", date);

    // assertEquals(offers, resultOffers);
    // }

    @Test
    public void getOffersByIdTest() throws InvalidTokenException, OfferNotFoundException {
        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authResponse,
                HttpStatus.OK);

        List<Offer> offers = new ArrayList<Offer>();
        offers.add(new Offer());

        when(authClient.verifyJwtToken("token")).thenReturn(response);
        when(offerRepository.findAllByEmployeeId(1)).thenReturn(offers);

        List<Offer> resultOffers = offerService.getOffersById("token", 1);
        assertEquals(offers, resultOffers);
    }

    @Test
    public void getPointsByIdTest() throws InvalidTokenException, OfferNotFoundException, InvalidEmployeeException {
        ResponseEntity<AuthorizationResponse> response = new ResponseEntity<AuthorizationResponse>(authResponse,
                HttpStatus.OK);

        List<Offer> offers = new ArrayList<Offer>();
        offers.add(new Offer());

        Employee emp = new Employee();
        when(authClient.verifyJwtToken("token")).thenReturn(response);
        when(empClient.getEmployee("token", 1)).thenReturn(emp);

        int points = offerService.getPointsById("token", 1);
        assertEquals(emp.getPointsGained(), points);
    }
}
