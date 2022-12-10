/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.Util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import static com.expastudios.blogweb.Util.SecurityContextVariables.Prefix;



@Slf4j
@RequiredArgsConstructor
public class TokenProvider {
	
	private static final String secret = "SECRET_PASSWORD";
	
	public static String GenerateToken (
	  UserDetails userDetails, int ExpireInHour )
	throws
	Exception {
		
		try {
			Algorithm algorithm = Algorithm.HMAC256 ( secret.getBytes ( ) );
			
			return JWT
			         .create ( )
			         .withSubject ( userDetails.getUsername ( ) )
			         .withClaim ( "roles", userDetails
			                                 .getAuthorities ( )
			                                 .stream ( )
			                                 .map ( GrantedAuthority::getAuthority )
			                                 .collect ( Collectors.toList ( ) ) )
			         .withIssuedAt ( new Date ( System.currentTimeMillis ( ) ) )
			         .withExpiresAt ( new Date ( System.currentTimeMillis ( ) + TimeUnit.HOURS.toMillis ( ExpireInHour ) ) )
			         .sign ( algorithm );
		} catch ( Exception exc ) {
			throw new Exception ( exc.getLocalizedMessage ( ) );
		}
	}
	
	public static String GenerateToken (
	  HttpServletRequest request, int ExpireInHour, Authentication authentication )
	throws
	Exception {
		
		try {
			Algorithm algorithm = Algorithm.HMAC256 ( secret.getBytes ( ) );
			User user = ( User ) authentication.getPrincipal ( );
			
			
			return JWT
			         .create ( )
			         .withSubject ( user.getUsername ( ) )
			         .withClaim ( "roles", user
				                             .getAuthorities ( )
				                             .stream ( )
				                             .map ( GrantedAuthority::getAuthority )
				                             .collect ( Collectors.toList ( ) ) )
			         .withIssuer ( request.getRequestURI ( ) )
			         .withExpiresAt ( new Date ( System.currentTimeMillis ( ) + TimeUnit.HOURS.toMillis ( ExpireInHour ) ) )
			         .sign ( algorithm );
		} catch ( Exception exc ) {
			throw new Exception ( exc.getLocalizedMessage ( ) );
		}
	}
	
	private static boolean isValidToken ( String token ) {
		
		return getUsernameWithToken ( token ) != null && !isExpired ( token );
	}
	
	public static String getUsernameWithToken ( String token ) {
		
		return getClaim ( token )
		         .get ( "sub" )
		         .asString ( );
	}
	
	private static Date getExpireDate ( String token ) {
		
		return getClaim ( token )
		         .get ( "exp" )
		         .asDate ( );
	}
	
	private static boolean isExpired ( String token ) {
		
		return getClaim ( token )
		         .get ( "exp" )
		         .asDate ( )
		         .before ( new Date ( System.currentTimeMillis ( ) ) );
	}
	
	private static Map < String, Claim > getClaim ( String token ) {
		
		DecodedJWT decodedJWT = JWT.decode ( token.substring ( Prefix.length ( ) ) );
		return decodedJWT.getClaims ( );
	}
	
}
