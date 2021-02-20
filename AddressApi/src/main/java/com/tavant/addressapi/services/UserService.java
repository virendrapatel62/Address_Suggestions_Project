package com.tavant.addressapi.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.tavant.addressapi.models.User;

@Service
public interface UserService {
	 public User createUser(User user);
	 public List<User> getUser();
	 public User getUserByEmail(String email);
}
