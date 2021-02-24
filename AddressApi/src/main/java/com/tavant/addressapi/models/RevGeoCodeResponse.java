package com.tavant.addressapi.models;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RevGeoCodeResponse {
	private Integer responseCode ;
	private String version ;
	private List<Address> results;
}
