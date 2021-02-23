package com.tavant.addressapi.controllers;

import java.io.InputStreamReader;

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

import com.tavant.addressapi.models.Address;
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
		try {

			byte[] bytes = file.getInputStream().readAllBytes();
			String csvContent = new String(bytes);
			Iterable<CSVRecord> records = CSVFormat.DEFAULT
					.parse(new InputStreamReader(file.getInputStream()));

			for (CSVRecord record : records) {
				record.iterator().forEachRemaining(value->{
					System.err.print(value + ", ");
				});
				System.err.println();
			}
			return ResponseEntity.status(HttpStatus.OK).body(csvContent);

		} catch (Exception e) {
			message = "Could not upload the file: " + file.getOriginalFilename() + "!";
			e.printStackTrace();
			return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body("error");
			
		}
	}
}
