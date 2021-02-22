package com.tavant.addressapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Address {
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
