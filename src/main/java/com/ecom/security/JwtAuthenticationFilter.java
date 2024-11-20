package com.ecom.security;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import ch.qos.logback.classic.Logger;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
	
	@Autowired
	private JwtHelper jwtHelper;
	@Autowired
	private UserDetailsService userDetailssevice;
	
	Logger logger=(Logger) LoggerFactory.getLogger(JwtAuthenticationEntryPoint.class);

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
				
		//get the token
		//Authorization = Bearer  51515184.11515fds.6158484 
		String requestToken=request.getHeader("Authorization");
		logger.info("message {}",requestToken);
		
		String jwttoken=null;
		String username=null;
		// System.out.println(requestToken);
		if(requestToken !=null && requestToken.trim().startsWith("Bearer")){
			
		 jwttoken = requestToken.substring(7);
		 try {
		  username= this.jwtHelper.getUsernameFromToken(jwttoken);
		 }catch(ExpiredJwtException e) {
			 logger.info("Invaild token message{}","Jwt token expired");
		 }catch(MalformedJwtException e){
			 
			 logger.info("Invaild token message{}","Invaild jwt Token");
		 }catch(IllegalArgumentException e) {
			 
			 logger.info("Invaild token message{}","unable to get Token"); 
		 }
		 
		 //validate
		 
		 if(username !=null && SecurityContextHolder.getContext().getAuthentication()==null){
			//validate 
			 
			UserDetails user = this.userDetailssevice.loadUserByUsername(username);
			 
			if(this.jwtHelper.validateToken(jwttoken, user)) {
				
				UsernamePasswordAuthenticationToken auth=new UsernamePasswordAuthenticationToken(user,null,user.getAuthorities());
				auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				
				SecurityContextHolder.getContext().setAuthentication(auth);
				
			}else {
				logger.info("not vaildated Massage {}","invaild jwt tocken");
			}
		 }else {
			 logger.info("Username Message {}","User Name is null or auth is already there");
		 }
		 
		}else {
			logger.info("token Message {}","Token doest not start with Brearer");
		}
		
		filterChain.doFilter(request, response);
	}

}
