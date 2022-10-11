package com.expastudios.blogweb.config;

import com.expastudios.blogweb.filter.CustomAuthenticationFilter;
import com.expastudios.blogweb.filter.CustomRequestFilter;
import com.expastudios.blogweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.http.HttpMethod.GET;



@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class Security {
  
  private final UserDetailsService userDetailsService;
  
  private final BCryptPasswordEncoder bCryptPasswordEncoder;
  
  private final UserRepository userRepository;
  
  @Bean
  SecurityFilterChain filterChain(HttpSecurity httpSecurity) throws Exception {
  
    // Configure AuthenticationManagerBuilder
    AuthenticationManagerBuilder authenticationManagerBuilder = httpSecurity.getSharedObject(AuthenticationManagerBuilder.class);
    authenticationManagerBuilder.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder);
  
    // Get AuthenticationManager
    AuthenticationManager authenticationManager = authenticationManagerBuilder.build();
  
  
    httpSecurity.
      cors().
      and().
      csrf().
      disable();
  
    httpSecurity.
      sessionManagement().
      sessionCreationPolicy(SessionCreationPolicy.STATELESS);
  
    httpSecurity.
      authorizeRequests().
      antMatchers("/login/**", "/api/register/**").permitAll().
      antMatchers(GET, "/api/user/**").hasAuthority("ROLE_USER");
  
    httpSecurity.
      authorizeRequests().
      anyRequest().
      authenticated();
    httpSecurity.formLogin();
  
    httpSecurity.
      addFilter(new CustomAuthenticationFilter(authenticationManager)).
    authenticationManager(authenticationManager);
    
    httpSecurity.
      addFilterBefore(new CustomRequestFilter(), CustomAuthenticationFilter.class);
  
    return httpSecurity.build();
  }
  
}
