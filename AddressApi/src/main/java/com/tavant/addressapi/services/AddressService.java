package com.tavant.addressapi.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tavant.addressapi.models.Address;
import com.tavant.addressapi.models.User;
import com.tavant.addressapi.repository.AddressRepository;

@Service
public class AddressService {
	@Autowired
	private AddressRepository addressRepository;
	
	public Address saveAddress(Address address) {
		return this.addressRepository.save(address);
	}
	
	public List<Address> getAllAddresses() {
		return this.addressRepository.findAll();
	}
	
	public Address getAddressByUser(User user) {
		return this.addressRepository.findByUser(user);
	}
}
