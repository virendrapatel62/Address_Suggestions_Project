package com.tavant.addressapi.models;

import javax.annotation.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.apache.commons.csv.CSVRecord;
import org.springframework.data.redis.core.RedisHash;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "address")
@Entity(name = "Address")
public class Address {
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE)
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

	public static Address getAddress(User user, CSVRecord record) throws RuntimeException {
		
		Address address = new Address(1L, user,
				record.get(0), 
				record.get(1), record.get(2), record.get(3), record.get(4), record.get(5), record.get(6), record.get(7),
				record.get(8), record.get(9), record.get(10),
				record.get(11), record.get(12), record.get(13),
				record.get(14),
				 Double.parseDouble(record.get(15))
				, Double.parseDouble(record.get(16)),
				record.get(17), Double.parseDouble(record.get(18))

		);
		return address;
	}
}
