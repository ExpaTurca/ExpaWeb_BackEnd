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

import javax.servlet.http.HttpServletRequest;
import java.util.Date;
import java.util.Map;
import java.util.stream.Collectors;

import static com.expastudios.blogweb.Util.SecurityContextVariables.Prefix;



@Slf4j
@RequiredArgsConstructor
public class TokenProvider {
	
	private static final String secret = "SECRET_PASSWORD";
	
	public static String GenerateToken (
	  HttpServletRequest request, int ExpireMinute, Authentication authentication )
	throws
	Exception {
		
		try {
			User user = ( User ) authentication.getPrincipal ( );
			Algorithm algorithm = Algorithm.HMAC256 ( secret.getBytes ( ) );
			
			return JWT
			         .create ( )
			         .withSubject ( user.getUsername ( ) )
			         .withExpiresAt ( new Date ( System.currentTimeMillis ( ) + ( long ) ExpireMinute * 60 * 1000 ) )
			         .withIssuer ( request.getRequestURI ( ) )
			         .withClaim ( "roles", user
				                             .getAuthorities ( )
				                             .stream ( )
				                             .map ( GrantedAuthority::getAuthority )
				                             .collect ( Collectors.toList ( ) ) )
			         .sign ( algorithm );
		} catch ( Exception exc ) {
			throw new Exception ( exc.getLocalizedMessage ( ) );
		}
	}
	
	public static boolean validateToken ( String token ) {
		
		return getUsernameToken ( token ) != null && !isExpired ( token );
	}
	
	public static String getUsernameToken ( String token ) {
		
		return getClaim ( token )
		         .get ( "sub" )
		         .asString ( );
	}
	
	public static Date getExpireDate ( String token ) {
		
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
