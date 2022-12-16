/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;
import java.util.List;



@SpringBootApplication
public class BlogWebApplication {
	
	public static void main ( String[] args ) {
		
		SpringApplication.run ( BlogWebApplication.class, args );
	}
	
	@Bean
	public CorsFilter corsConfigurer ( ) {
		
		CorsConfiguration corsConfiguration = new CorsConfiguration ( );
		corsConfiguration.setAllowCredentials ( true );
		corsConfiguration.setAllowedMethods ( Arrays.asList ( "POST", "GET", "PUT", "DELETE", "OPTIONS" ) );
		corsConfiguration.setAllowedOrigins ( List.of ( "http://localhost:4200" ) );
		corsConfiguration.setAllowedHeaders (
		  Arrays.asList ( "Origin", "Access-Control-Allow-Origin", "Content-Type", "Accept", "Authorization",
		                  "Origin, Accept", "X-Requested-With", "Access-Control-Request-Method",
		                  "Access-Control-Request-Headers" ) );
		corsConfiguration.setExposedHeaders (
		  Arrays.asList ( "Origin", "Content-Type", "Accept", "Authorization", "Access-Control-Allow-Origin",
		                  "Access-Control-Allow-Origin", "Access-Control-Allow-Credentials" ) );
		
		corsConfiguration.setMaxAge ( 3600L );
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource ( );
		source.registerCorsConfiguration ( "/**", corsConfiguration );
		return new CorsFilter ( source );
	}
	
}
