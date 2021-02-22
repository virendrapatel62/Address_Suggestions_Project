package com.tavant.addressapi;

import java.net.URI;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.Nullable;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import com.tavant.addressapi.models.AddressSearchResponse;
import com.tavant.addressapi.models.MapAuthResponse;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/map")
public class MapController {
	
	private String token;
	
	@GetMapping("/addresses")
	public ResponseEntity<?> getAddresses(@RequestParam @Nullable String address , @RequestParam @Nullable Integer itemCount) throws Exception{
		
		String url = "https://atlas.mapmyindia.com/api/places/geocode";
		UriComponentsBuilder builder =  UriComponentsBuilder.fromHttpUrl(url);
		
		if(address==null) {
			return new ResponseEntity<>("address is required in query", HttpStatus.BAD_REQUEST);
		}
		
		builder = builder.queryParam("address", address);
		int count = 5;		
		if(itemCount!=null) {
			count = itemCount;
		}
		
		builder = builder.queryParam("itemCount", count);
		
		
		
		HttpMethod method = HttpMethod.GET;
		
		MultiValueMap<String, String> header = new HttpHeaders();
		header.add("Authorization", "Bearer "+this.getMapToken());
		

		System.err.println(builder.toUriString());
		RequestEntity entity = new RequestEntity(null , header, method, new URI(builder.toUriString()));
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<Object> response =  restTemplate.exchange(entity, Object.class );
				
		return ResponseEntity.ok( response.getBody());
	}
	
	private String getMapToken(){
		
		if(token != null ) {
			System.err.println("Getting Previous Token");
			return token ;
		}
		
		RestTemplate restTemplate = new RestTemplate();
		ResponseEntity<MapAuthResponse> responseEntity = restTemplate.postForEntity(
				"https://outpost.mapmyindia.com/api/security/oauth/token?grant_type=client_credentials&client_id=K9FcE7rTjKaqju2XuWlLrzxXYspxv4s-A9hdogM0WDmcUVwsFo0wBA==&client_secret=Lnb4Nczh7cGDCx-MmiftR99nD8YZcpUeMLGlwmXgt0PW0P_jU7UJQhVRmghgP-Rk", 
				null , MapAuthResponse.class);
		this.token = responseEntity.getBody().access_token;
		return this.token;
	}
}
