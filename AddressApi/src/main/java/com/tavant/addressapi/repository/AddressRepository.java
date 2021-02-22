package com.tavant.addressapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Repository;

import com.tavant.addressapi.models.Address;
import com.tavant.addressapi.models.User;

@Repository
public interface AddressRepository extends JpaRepository<Address, Long> {

}
