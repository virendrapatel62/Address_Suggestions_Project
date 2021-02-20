package com.tavant.addressapi.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import com.tavant.addressapi.models.User;

public interface UserRepository extends JpaRepository<User, Long> {
	public User findByEmail(String email);
}
