package com.cognizant.offers.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Offer")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Offer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    // offer name
    private String name;

    // offerdescription
    private String description;

    // category of the offer
    private String category;

    // @Column(name = "emp_id", insertable = false, updatable = false)
    // private int empId;

    // opening/creation date of the offer
    @Column(name = "open_date")
    private Date openDate;

    // engaged date ( when a buyer shows interest)
    @Column(name = "engaged_date")
    private Date engagedDate;
    private int likes;

    // when the buyer buys
    @Column(name = "closed_date")
    private Date closedDate;

    // employee who created the Offer
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "emp_id")
    private Employee emp;

    // employee which showed interest in the offer
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "engaged_emp_id")
    private Employee engagedEmp;

    // many employees can like many offers
    @JsonIgnore
    @ManyToMany(mappedBy = "likedOffers", fetch = FetchType.EAGER)
    private Set<Employee> likedByEmployees = new HashSet<>();

}
