/***************************************************************
 * Copyright (c) 2022
 **************************************************************/

package com.expastudios.blogweb.Util;

import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import java.util.*;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserDetailsImpl implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Override
	public UserDetails loadUserByUsername ( String username )
	throws
	UsernameNotFoundException {
		
		Optional < User > user = userRepository
		                           .findByEmailAndIsActiveTrue ( username )
		                           .stream ( )
		                           .findFirst ( );
		Collection < SimpleGrantedAuthority > authorities = new ArrayList <> ( );
		try {
			user.ifPresentOrElse ( x -> x
			                              .getRoles ( )
			                              .forEach ( role -> {
				                              authorities.add ( new SimpleGrantedAuthority ( role.getName ( ) ) );
			                              } ), ( ) -> { throw new UsernameNotFoundException ( "User not found!" ); } );
			
			return new org.springframework.security.core.userdetails.User (
			  user
				.orElseThrow ( )
				.getEmail ( ), user
				                 .get ( )
				                 .getPassword ( ), authorities );
		} catch ( UsernameNotFoundException exc ) {
			throw new UsernameNotFoundException ( username );
		}
	}
	
}
