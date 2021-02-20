package com.tavant.addressapi.controllers;

import java.lang.System.Logger;
import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tavant.addressapi.exceptions.DuplicateEmailException;
import com.tavant.addressapi.models.AuthenticationRequest;
import com.tavant.addressapi.models.AuthenticationResponse;
import com.tavant.addressapi.models.KeyValueErrorResponse;
import com.tavant.addressapi.models.User;
import com.tavant.addressapi.services.UserDetailsServiceImpl;
import com.tavant.addressapi.services.UserService;
import com.tavant.addressapi.utils.JwtUtils;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserDetailsServiceImpl userDetailService;

	@Autowired
	private UserService userService;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@PostMapping("/login")
	public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationRequest credentials) {
		

//		Authenticating User
		Authentication authentication = this.authenticationManager.authenticate(
				new UsernamePasswordAuthenticationToken(credentials.getEmail(), credentials.getPassword()));

		User user = (User) authentication.getPrincipal();

//		token generation
		JwtUtils jwtUtils = new JwtUtils();
		String token = jwtUtils.generateToken(user);
		
//		Success response ,Exception will be handled by ControllerAdviser
		return new ResponseEntity<Object>(new AuthenticationResponse(token), HttpStatus.OK);

	}

	@PostMapping("/register")
	public ResponseEntity<?> register(@RequestBody @Valid User user) {
	

//		checking Esisting user with given email ID
		if (this.userService.getUserByEmail(user.getEmail()) != null) {
			throw new DuplicateEmailException(user.getEmail());
		}
		
//		password matching 
		if (!user.getPassword().trim().equals(user.getPassword2().trim())) {

			Object errors = new KeyValueErrorResponse().addError("error", "password and password2 not matched !")
					.getMap();
			return ResponseEntity.badRequest().body(errors);

		}
		
		String rawPassword = user.getPassword();
		
//		passoword encoding
		user.setPassword(passwordEncoder.encode(rawPassword));
		
		User newUser = this.userService.createUser(user);
		
//		login in after successfull registration
		return this.login(new AuthenticationRequest(user.getEmail(), rawPassword));
	}
}
