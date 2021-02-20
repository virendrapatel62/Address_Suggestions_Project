package com.tavant.addressapi.models;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@Data
@NoArgsConstructor
public class AuthenticationRequest {
	
	@Email
	String email ;
	
	@NotBlank
	String password;
}
