package com.tavant.addressapi.exceptions;

import lombok.Data;

@Data
public class DuplicateEmailException extends RuntimeException {
	
	private String rejectedValue;
	
	
	public DuplicateEmailException(String rejectedValue) {
		super("Email Already Exists !");
		this.rejectedValue = rejectedValue;
	}
	public DuplicateEmailException(String rejectedValue , String message) {
		super(message);
		this.rejectedValue = rejectedValue;
	}

}
