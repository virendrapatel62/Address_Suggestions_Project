package com.tavant.addressapi.models;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name="address")
@Entity(name="Address")
public class Address {
	@Id @GeneratedValue(strategy =  GenerationType.SEQUENCE)
	private Long id;
	
	@OneToOne
	private User user;
	 public String houseNumber;
	    public String houseName;
	    public String poi;
	    public String street;
	    public String subSubLocality;
	    public String subLocality;
	    public String locality;
	    public String village;
	    public String subDistrict;
	    public String district;
	    public String city;
	    public String state;
	    public String pincode;
	    public String formattedAddress;
	    public String eLoc;
	    public double latitude;
	    public double longitude;
	    public String geocodeLevel;
	    public double confidenceScore;
}
