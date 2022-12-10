/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.Util.TokenProvider;
import com.expastudios.blogweb.config.AuthenticationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



@RestController
@RequestMapping("/authentication")
@RequiredArgsConstructor
public class AuthenticationEndpoint {
	
	private final AuthenticationManager authenticationManager;
	
	private final UserDetailsService userDetailsService;
	
	@PostMapping ( "/login" )
	public ResponseEntity < ? > authenticate (
	  @RequestBody AuthenticationRequest authRequest, HttpServletRequest request, HttpServletResponse response )
	throws
	Exception {
		
		final UserDetails userDetails = userDetailsService.loadUserByUsername ( authRequest.getEmail ( ) );
		
		authenticationManager.authenticate (
		  new UsernamePasswordAuthenticationToken ( authRequest.getEmail ( ), authRequest.getPassword ( ),
		                                            userDetails.getAuthorities ( ) ) );
		return ResponseEntity.ok ( TokenProvider.GenerateToken ( userDetails, 24 ) );
	}
	
}
