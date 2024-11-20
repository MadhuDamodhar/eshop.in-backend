package com.ecom.Service.Imp;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.ecom.Exception.ResourceNotFoundException;
import com.ecom.Model.User;
import com.ecom.Repository.UserRepository;
@Service
public class CustomUserDetailsService implements UserDetailsService{
	
	@Autowired
	UserRepository userRepo;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		// Load user From database
		System.out.println("loading user from database");
		User findByEmail = this.userRepo.findByEmail(username).orElseThrow(() -> new ResourceNotFoundException("User Not Found"));
		return findByEmail;
	}
	
	
	

}
