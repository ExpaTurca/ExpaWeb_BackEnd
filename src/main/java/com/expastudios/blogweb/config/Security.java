/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.config;

import com.expastudios.blogweb.Util.CustomAuthenticationHandler;
import com.expastudios.blogweb.filter.CustomAuthenticationFilter;
import com.expastudios.blogweb.filter.CustomRequestFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import static org.springframework.http.HttpMethod.GET;



@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security  {
	
	private final UserDetailsService userDetailsService;
	
	/*@Bean
	public CorsWebFilter corsWebFilter ( ) {
		
		CorsConfiguration corsConfig = new CorsConfiguration ( );
		corsConfig.setAllowedOrigins ( List.of ( "http://localhost:4200" ) );
		corsConfig.setMaxAge ( 3600L );
		corsConfig.addAllowedMethod ( "*" );
		corsConfig.addAllowedHeader ( "Requester-Type" );
		corsConfig.addExposedHeader ( "X-Get-Header" );
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource ( );
		source.registerCorsConfiguration ( "/**", corsConfig );
		
		return new CorsWebFilter ( source );
	}*/
	
	@Bean
	SecurityFilterChain filterChain ( HttpSecurity httpSecurity )
	throws
	Exception {
		
		AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject (
		  AuthenticationManagerBuilder.class );
		authenticationManagerBuilder.authenticationProvider ( daoAuthenticationProvider ( ) );
		AuthenticationManager authenticationManager = authenticationManagerBuilder.build ( );
		
		httpSecurity
		  .cors ( )
		  .disable ( )
		  .csrf ( )
		  .disable ( );
		
		httpSecurity
		  .sessionManagement ( )
		  .sessionCreationPolicy ( SessionCreationPolicy.STATELESS );
		
		httpSecurity
		  .authorizeRequests ( )
		  .antMatchers ( "/login/**" )
		  .anonymous ( )
		  .antMatchers ( "/**/user/create/**", "/**/role/**" )
		  .permitAll ( )
		  .antMatchers ( GET, "/**/user/**", "/**/home/**" )
		  .hasAuthority ( "ROLE_USER" );
		
		httpSecurity
		  .authorizeRequests ( )
		  .anyRequest ( )
		  .authenticated ( );
		
		httpSecurity
		  .formLogin ( )
		  .successHandler ( customAuthenticationHandler ( ) )
		  .and ( )
		  .logout ( )
		  .deleteCookies ( "remove" )
		  .invalidateHttpSession ( false );
		
		httpSecurity
		  .addFilter ( new CustomAuthenticationFilter ( authenticationManager ) )
		  .authenticationManager ( authenticationManager );
		
		httpSecurity.addFilterBefore ( new CustomRequestFilter ( ), CustomAuthenticationFilter.class );
		
		return httpSecurity.build ( );
	}
	
	@Bean
	public AuthenticationSuccessHandler customAuthenticationHandler ( ) {
		
		return new CustomAuthenticationHandler ( );
	}
	
	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider ( ) {
		
		DaoAuthenticationProvider provider = new DaoAuthenticationProvider ( );
		provider.setPasswordEncoder ( passwordEncoder ( ) );
		provider.setUserDetailsService ( userDetailsService );
		System.out.println ("prov: " + provider);
		return provider;
	}
	
	@Bean
	public BCryptPasswordEncoder passwordEncoder ( ) {
		
		return new BCryptPasswordEncoder ( );
	}
	
}
