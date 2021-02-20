package com.tavant.addressapi.models;

import java.util.HashMap;
import java.util.Map;

public class KeyValueErrorResponse {
	
	Map<String, Object>  errors = new HashMap<>(); 
	
	public KeyValueErrorResponse addError(String key , Object value){
		this.errors.put(key, value);
		return this;
	}
	
	public Map getMap() {
		return this.errors;
	}
}
