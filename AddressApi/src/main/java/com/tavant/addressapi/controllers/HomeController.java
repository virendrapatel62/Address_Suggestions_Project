package com.tavant.addressapi.controllers;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class HomeController {
	
	@GetMapping()
	public String api() {
		System.out.println( SecurityContextHolder.getContext().getAuthentication());
		return "Api is Working ✅";
	}
	@GetMapping("/public")
	public String test() {
		return "public Api is Working ..";
	}
}
