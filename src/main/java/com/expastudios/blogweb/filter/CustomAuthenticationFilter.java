/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.filter;

import com.expastudios.blogweb.Util.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RequiredArgsConstructor
	public class CustomAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
	
	@Autowired private final AuthenticationManager authenticationManager;
	
	@Override
	public Authentication attemptAuthentication ( HttpServletRequest request, HttpServletResponse response )
	throws
	AuthenticationException {
		
		try {
			String username = request.getParameter ( "username" );
			String password = request.getParameter ( "password" );
			
			UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken (
			  username, password );
			return authenticationManager.authenticate ( authenticationToken );
		} catch ( AuthenticationException exc ) {
			log.error ( exc.getLocalizedMessage ( ) );
			return null;
		}
	}
	
	@Override
	protected void successfulAuthentication (
	  HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication ) {
		
		try {
			String access_token = TokenProvider.GenerateToken ( request, 1,authentication );
			String refresh_token = TokenProvider.GenerateToken ( request, 24,authentication );
			
			Map < String, String > token = new HashMap <> ( );
			token.put ( "access_token", access_token );
			token.put ( "refresh_token", refresh_token );
			
			response.setContentType ( MediaType.APPLICATION_JSON_VALUE );
			
			response.setHeader ( "access_token", access_token );
			response.setHeader ( "refresh_token", refresh_token );
			
			new ObjectMapper ( ).writeValue ( response.getOutputStream ( ), token );
		} catch ( Exception exc ) {
			log.error ( exc.getLocalizedMessage ( ) );
		}
	}
	
	@Override
	protected void unsuccessfulAuthentication (
	  HttpServletRequest request, HttpServletResponse response, AuthenticationException failed )
	throws
	IOException,
	ServletException {
		
		try {
			Map < String, String > failed_login = new HashMap <> ( );
			failed_login.put ( "failed_login", failed.getLocalizedMessage ( ) );
			response.setContentType ( MediaType.APPLICATION_JSON_VALUE );
			
			response.setHeader ( "failed_login", failed.getLocalizedMessage ( ) );
			
			new ObjectMapper ( ).writeValue ( response.getOutputStream ( ), failed_login );
		} catch ( Exception exc ) {
			log.error ( exc.getLocalizedMessage ( ) );
		}
	}
	
}
