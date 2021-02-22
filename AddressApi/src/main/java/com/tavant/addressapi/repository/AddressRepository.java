package com.tavant.addressapi.repository;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.tavant.addressapi.models.Address;
import com.tavant.addressapi.models.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {
	public Address findByUser(User user);
}
