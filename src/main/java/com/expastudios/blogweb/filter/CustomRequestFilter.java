/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.filter;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.*;

import static com.expastudios.blogweb.Util.SecurityContextVariables.Prefix;
import static com.expastudios.blogweb.Util.SecurityContextVariables.Secret;
import static java.util.Arrays.stream;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;
import static org.springframework.http.HttpStatus.FORBIDDEN;

@Slf4j
public class CustomRequestFilter extends OncePerRequestFilter {
	
	@Override
	protected void doFilterInternal (
	  HttpServletRequest request, HttpServletResponse response, FilterChain filterChain )
	throws
	ServletException,
	IOException {
		
		if (
		  request.getServletPath ( ).equals ( "/login" )
		  || request.getServletPath ( ).equals ( "/api/user/create" )
		  || request.getRequestURI ( ).equals ( "/api/role/create" )
		  || request.getServletPath ().equals ( "/api/role/adduser" ) ) {
			filterChain.doFilter ( request, response );
		} else {
			final String authorization_header = request.getHeader ( AUTHORIZATION );
			if ( authorization_header != null && authorization_header.startsWith ( Prefix ) ) {
				try {
					String token = authorization_header.substring ( Prefix.length ( ) );
					Algorithm algorithm = Algorithm.HMAC256 ( Secret.getBytes ( ) );
					
					JWTVerifier verifier = JWT
					                         .require ( algorithm )
					                         .build ( );
					DecodedJWT decodedJWT = verifier.verify ( token );
					
					String username = decodedJWT.getSubject ( );
					
					String[] roles = decodedJWT
					                   .getClaim ( "roles" )
					                   .asArray ( String.class );
					
					Collection < SimpleGrantedAuthority > authorities = new ArrayList <> ( );
					
					stream ( roles ).forEach ( role -> {
						authorities.add ( new SimpleGrantedAuthority ( role ) );
					} );
					
					UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken (
					  username, null, authorities );
					
					SecurityContextHolder
					  .getContext ( )
					  .setAuthentication ( authenticationToken );
					filterChain.doFilter ( request, response );
				} catch ( Exception exc ) {
					log.error ( "Error logging in: {} ", exc.getMessage ( ) );
					response.setContentType ( MediaType.APPLICATION_JSON_VALUE );
					response.setHeader ( "error", exc.getMessage ( ) );
					response.setStatus ( FORBIDDEN.value ( ) );
					Map < String, String > error = new HashMap <> ( );
					error.put ( "error_message", exc.getMessage ( ) );
					new ObjectMapper ( ).writeValue ( response.getOutputStream ( ), error );
				}
			} else {
				String error_message = "Token is missing";
				Map < String, String > error = new HashMap <> ( );
				error.put ( "error_message", error_message );
				
				response.setContentType ( MediaType.APPLICATION_JSON_VALUE );
				response.setHeader ( "error", error_message );
				response.setStatus ( FORBIDDEN.value ( ) );
				new ObjectMapper ( ).writeValue ( response.getOutputStream ( ), error );
			}
		}
		
	}
	
}
