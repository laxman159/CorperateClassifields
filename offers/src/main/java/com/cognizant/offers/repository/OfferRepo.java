package com.cognizant.offers.repository;

import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import com.cognizant.offers.models.Offer;

/*
 * 
 * Author: Laxman Sharma
 * Class : OfferRepo, repository  container for the database table named offer 
 * Date created: 23-JUN-2022
 * 
 */
public interface OfferRepo extends CrudRepository<Offer, Integer> {

    public List<Offer> getOfferByCategory(String category);

    public List<Offer> findAll(Pageable pageable);

    @Query("from Offer where day(open_date)=?1 and month(open_date)=?2 and year(open_date)=?3")
    public List<Offer> getOfferByPostedDate(int day, int month, int year);

    @Query("from Offer where emp_id =?1")
    public List<Offer> findAllByEmployeeId(int id);

    @Query("select distinct(category) from Offer")
    public List<String> getAllDistinctCategory();
}
