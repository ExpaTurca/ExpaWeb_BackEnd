/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;



@SpringBootApplication
public class BlogWebApplication {
	
	public static void main ( String[] args ) {
		
		SpringApplication.run ( BlogWebApplication.class, args );
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder ( ) {

		return new BCryptPasswordEncoder ( );
	}
	
}
