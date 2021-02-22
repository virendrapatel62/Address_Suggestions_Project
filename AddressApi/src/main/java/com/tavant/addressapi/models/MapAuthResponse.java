package com.tavant.addressapi.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MapAuthResponse {
	public String access_token;
    public String token_type;
    public int expires_in;
    public String scope;
    public String project_code;
    public String client_id;
}
