package com.tavant.addressapi.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.tavant.addressapi.models.MapAuthResponse;

@RestController

//@CrossOrigin("*")
@RequestMapping("/api")
public class HomeController {
	
	@GetMapping()
	public String api() {
		System.out.println( SecurityContextHolder.getContext().getAuthentication());
		return "Api is Working ✅";
	}
	@GetMapping("/public")
	public Object test() {
		
		return "Public Api ";
	}
}
