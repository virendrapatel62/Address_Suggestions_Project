package com.tavant.addressapi.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Length;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "users")
@Entity(name = "users")
@Data

@NoArgsConstructor
public class User implements UserDetails {
	
	@Id @GeneratedValue(strategy =  GenerationType.SEQUENCE)
	private Long Id;
	
	@NotBlank
	@NotNull
	@Length(min = 4 , max = 20)
	private String name ; 
	
	
	@Email
	@NotNull
	private String email ; 

	@NotBlank
	@Length(min=8 , max = 200)
	@NotNull
	private String password ;
	
	
	@NotBlank
	@Length(min=8 , max = 200)
	@NotNull
	@Transient
	private String password2 ;
	

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return new ArrayList<>();
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return this.password;
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return this.email;
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}

	public User(Long id, @NotBlank @NotNull @Length(min = 4, max = 20) String name, @Email @NotNull String email,
			@NotBlank @Length(min = 8, max = 200) @NotNull String password) {
		super();
		Id = id;
		this.name = name;
		this.email = email;
		this.password = password;
	}

	
}
