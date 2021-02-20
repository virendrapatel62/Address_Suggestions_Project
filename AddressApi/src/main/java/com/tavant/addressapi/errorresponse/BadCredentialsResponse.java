package com.tavant.addressapi.errorresponse;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class BadCredentialsResponse {
	private String error = "Email Or password incorrect";
	
}
