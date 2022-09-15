package com.cts.pointsmicroservice.model;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Offer {
	
	private int id;
	private String name;
	private String description;
	private String category;
	private Date openDate;
	private Date engagedDate;
	private Date closedDate;
	private int likes;
}

