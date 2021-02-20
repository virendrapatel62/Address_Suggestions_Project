package com.tavant.addressapi.filters;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.hibernate.annotations.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.web.configurers.SecurityContextConfigurer;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.tavant.addressapi.models.User;
import com.tavant.addressapi.utils.JwtUtils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.SignatureException;

@Component
public class JwtRequestFilter extends OncePerRequestFilter {

	@Autowired
	private UserDetailsService userDetailsService;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Inside Filter...");
		System.out.println("SecurityContextHolder.getContext().getAuthentication()" + SecurityContextHolder.getContext().getAuthentication());
		
		JwtUtils jwtUtil = new JwtUtils();
		final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

       try {
    	   if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
               jwt = authorizationHeader.substring(7);
               username = jwtUtil.extractUsername(jwt);
           }


           if (username != null) {

               UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

               if (jwtUtil.validateToken(jwt, userDetails)) {

                   UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                           userDetails, null, userDetails.getAuthorities());
                   usernamePasswordAuthenticationToken
                           .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                   SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
               }
           }
	} catch (Exception e) {
		// TODO: handle exception
		e.printStackTrace();
	}
        filterChain.doFilter(request, response);
	}

}
