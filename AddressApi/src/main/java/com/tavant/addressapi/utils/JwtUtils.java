package com.tavant.addressapi.utils;

import java.time.LocalDate;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.hibernate.cfg.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;

import com.sun.el.lang.FunctionMapperImpl.Function;
import com.tavant.addressapi.models.User;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;


public class JwtUtils {
	
	@Autowired
	Environment environment;
	
	String secret = "privateKey";
	
	public JwtUtils() {
//		 this.secret = environment.getProperties().getProperty("JWT_SECRET");
	}
	public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, java.util.function.Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }
    private Claims extractAllClaims(String token) {
        return Jwts.parser().setSigningKey(secret).parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String generateToken(User userDetails) {
        Map<String, Object> claims = new HashMap();
        claims.put("id", userDetails.getId());
        claims.put("email", userDetails.getEmail());
        claims.put("name", userDetails.getName());
        return createToken(claims, userDetails.getEmail());
    }

    private String createToken(Map<String, Object> claims, Object subject) {

        return Jwts.builder().setClaims(claims).setSubject(subject.toString()).setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
                .signWith(SignatureAlgorithm.HS256, secret).compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
