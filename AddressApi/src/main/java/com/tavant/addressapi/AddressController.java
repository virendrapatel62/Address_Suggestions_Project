package com.tavant.addressapi;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
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
	public ResponseEntity<?> saveAddress(@RequestBody Address address){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		System.err.println(user);
		System.err.println(address);
		
		address.setUser(user);
		Address userAddress = this.addressService.getAddressByUser(user); 

		if(userAddress!=null) {
			address.setId(userAddress.getId());
		}
		
		address = this.addressService.saveAddress(address);
		return ResponseEntity.ok(address);
	}
	
	@GetMapping
	public ResponseEntity<?> getAddresses(){
		return ResponseEntity.ok(this.addressService.getAllAddresses());
	}
	@GetMapping("/me")
	public ResponseEntity<?> getAddress(){
		User user = (User)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return ResponseEntity.ok(this.addressService.getAddressByUser(user));
	}
	
}
