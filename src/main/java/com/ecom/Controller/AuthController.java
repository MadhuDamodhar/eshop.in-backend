package com.ecom.Controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecom.Exception.BadUserLoginDetailsException;
import com.ecom.Service.Imp.CustomUserDetailsService;
import com.ecom.payload.JwtRequest;
import com.ecom.payload.JwtResponse;
import com.ecom.payload.UserDto;
import com.ecom.security.JwtHelper;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {
	@Autowired
	private AuthenticationManager manger;
	@Autowired
	private CustomUserDetailsService userDrtailsservice;
	@Autowired
	private JwtHelper jwthelper;
	@Autowired
	private ModelMapper mapper;
	@PostMapping("/login")
	public ResponseEntity<JwtResponse> login(@RequestBody JwtRequest request)throws Exception{

		this.authenticateUser(request.getUsername(),request.getPassword());
		UserDetails UserDetail = this.userDrtailsservice.loadUserByUsername(request.getUsername());
		
		/*  
		 * if username and password both are Correct then get token by line no *47
		 * create jwtResponse(this class created by my) object
		 * Under JwtResponse class there is entity token
		 * 
		 */
		
		String token = this.jwthelper.generateToken(UserDetail);
		
		JwtResponse response =new JwtResponse();
		response.setToken(token);
		response.setUser(this.mapper.map(UserDetail,UserDto.class));
		return new ResponseEntity<JwtResponse>(response,HttpStatus.OK);
	}
	

	//Authenticate
	private void authenticateUser(String username, String password) {
		try {
		manger.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}catch(BadCredentialsException e){
			throw new BadUserLoginDetailsException("Invaild Username or Password");
			
		}catch(DisabledException e) {
			throw new BadUserLoginDetailsException("User is disable");
		}
		}

}
