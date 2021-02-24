package com.tavant.addressapi.controllers;

import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tavant.addressapi.models.Address;
import com.tavant.addressapi.models.RevGeoCodeResponse;
import com.tavant.addressapi.models.User;
import com.tavant.addressapi.services.AddressService;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/address")
public class AddressController {

	@Autowired
	private AddressService addressService;
	

	@PostMapping
	public ResponseEntity<?> saveAddress(@RequestBody Address address) {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.err.println(user);
		System.err.println(address);

		address.setUser(user);
		

		address = this.addressService.saveAddress(address);
		return ResponseEntity.ok(address);
	}

	@GetMapping
	public ResponseEntity<?> getAddresses() {
		return ResponseEntity.ok(this.addressService.getAllAddresses());
	}

	@GetMapping("/me")
	public ResponseEntity<?> getAddress() {
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(this.addressService.getAddressByUser(user));
	}

	@PostMapping("/file")
	public ResponseEntity<?> uploadFile(@RequestParam("file") org.springframework.web.multipart.MultipartFile file) {
		String message = "";
		String headers = "";
		
		User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		List<Address>  addresses = new ArrayList<>();
		List<Address>  invalidAddresses = new ArrayList<>();
		List<Address>  validAddresses = new ArrayList<>();
		try {
			byte[] bytes = file.getInputStream().readAllBytes();
			String csvContent = new String(bytes);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT
					.parse(new InputStreamReader(file.getInputStream()));

			for (CSVRecord record : records) {
				if(record.getRecordNumber()>1) {
					Address address = Address.getAddress(user, record);
					
					if(isValidAddress(address)) {
					
						validAddresses.add(addressService.saveAddress(address));
					}else {
						invalidAddresses.add(address);
					}
				}else {
					headers = record.getParser().getHeaderNames().toString();
				}
			}
			Map<String, Object> resp = new HashMap<>();
			
			resp.put("totalAddresses", validAddresses.size()+invalidAddresses.size());
			resp.put("validAddresses", validAddresses);
			resp.put("invalidAddresses", invalidAddresses);
			resp.put("error", "failed to validate addresses in map , provide valid addresses");
			
			return ResponseEntity.status(HttpStatus.OK).body(resp);

		} catch (Exception e) {
			message = "file format is not valid , file must have thease headers \n " + headers;
			e.printStackTrace();
			Map<String, String> resp = new HashMap<>();
			resp.put("error", message);
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(resp);
			
		}
	}
	
	private boolean isValidAddress(Address address) {
		String raw = "https://apis.mapmyindia.com/advancedmaps/v1/l2dlc7zabjd6wx8ekzjo2ezzvt21i8b3/rev_geocode?lng=%s&lat=%s";
		String url = String.format(raw, address.getLongitude() , address.getLatitude());
		System.err.println(url);
		
		RestTemplate restTemplate = new RestTemplate();
		RevGeoCodeResponse response=  restTemplate.getForObject(url, RevGeoCodeResponse.class);
		if(response.getResponseCode() == 200 && response.getResults().size()>0) {
			return true;
		}
		return false;
	}
	
	
}
