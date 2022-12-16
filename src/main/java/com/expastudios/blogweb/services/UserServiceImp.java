/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.*;
import com.expastudios.blogweb.entity.Role;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.model.UserDTO;
import com.expastudios.blogweb.repository.RoleRepository;
import com.expastudios.blogweb.repository.UserRepository;
import com.expastudios.blogweb.services.IServices.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.HibernateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;



@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService, UserDetailsService {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private RoleRepository roleRepository;
	
	@Override
	public UserDetails loadUserByUsername ( String username ) {
		
		Optional < User > user = userRepository
		                           .findByEmailAndIsActiveTrue ( username )
		                           .stream ( )
		                           .findFirst ( );
		
		Collection < SimpleGrantedAuthority > authorities = new ArrayList <> ( );
		
		user.ifPresentOrElse ( x -> x
		                              .getRoleSet ( )
		                              .forEach ( role -> {
			                              authorities.add ( new SimpleGrantedAuthority ( role.getName ( ) ) );
		                              } ), ( ) -> {
			throw new UsernameNotFoundException ( "Username not found!" );
		} );
		System.out.println ( "usr: " + user.orElseThrow ( ) );
		
		return new org.springframework.security.core.userdetails.User (
		  user
			.orElseThrow ( )
			.getEmail ( ), user
			                 .get ( )
			                 .getPassword ( ), authorities );
	}
	
	@Override
	public ResponseEntity < UserDTO > getUser ( String email ) {
		
		Optional < UserDTO > profileDTO = userRepository
		                                    .findByEmailAndIsActiveTrue ( email )
		                                    .filter ( User::isActive )
		                                    .map ( ( map ) -> {
			                                    try {
				                                    return ( UserDTO ) EntityDtoConversion.ConvertToDTO ( map );
			                                    } catch ( ClassNotFoundException e ) {
				                                    throw new RuntimeException ( e );
			                                    }
		                                    } );
		
		return new ResponseEntity <> ( profileDTO.orElseThrow ( ), HttpStatus.OK );
	}
	
	@Override
	public ResponseEntity < ? > saveUser (
	  User user, HttpServletRequest request, HttpServletResponse response ) {
		
		try {
			user.setPassword ( PasswordEncoder.encode ( user.getPassword ( ) ) );
			user.setActive ( true );
			user.setRegisteredAt ( Zone.getCurrentTime ( ) );
			userRepository.save ( user );
		} catch ( HibernateException e ) {
			throw new RuntimeException ( e );
		}
		return ResponseEntity
		         .ok ( )
		         .build ( );
		
	}
	
	@Override
	public ResponseEntity < Boolean > editUser ( User user, HttpServletRequest request, HttpServletResponse response ) {
		
		userRepository.save ( user );
		
		return ResponseEntity
		         .ok ( )
		         .build ( );
	}
	
	@Override
	public ResponseEntity < Role > getRole ( String roleName ) {
		
		Optional < Role > role = roleRepository.findFirstByName ( roleName );
		return new ResponseEntity <> ( role.orElseThrow ( ), HttpStatus.OK );
	}
	
	@Override
	public ResponseEntity < Boolean > saveRole ( String name ) {
		
		Role roleEntity = new Role ( );
		roleEntity.setName ( name );
		roleRepository.save ( roleEntity );
		return new ResponseEntity <> ( true, HttpStatus.CREATED );
	}
	
	@Override
	public ResponseEntity < Boolean > deleteRole ( String name ) {
		
		try {
			Role roleEntity = roleRepository
			                    .findFirstByName ( name )
			                    .orElseThrow ( );
			roleRepository.delete ( roleEntity );
		} catch ( HibernateException e ) {
			throw new RuntimeException ( e );
		}
		
		return new ResponseEntity <> ( true, HttpStatus.OK );
	}
	
	@Override
	public ResponseEntity < Boolean > addRoleToUser ( UUID userId, String roleName ) {
		
		Optional < User > user = userRepository.findByIdAndIsActiveTrue ( userId );
		Optional < Role > roleEntity = roleRepository
		                                 .findFirstByName ( roleName )
		                                 .stream ( )
		                                 .findFirst ( );
		
		roleEntity
		  .orElseThrow ( )
		  .getUserSet ( )
		  .add ( user.orElseThrow ( ) );
		
		user
		  .orElseThrow ( )
		  .getRoleSet ( )
		  .add ( roleEntity.orElseThrow ( ) );
		
		userRepository.save ( user.orElseThrow ( ) );
		roleRepository.save ( roleEntity.orElseThrow ( ) );
		
		log.info ( "New Role {} added to user!", roleName );
		return new ResponseEntity <> ( true, HttpStatus.CREATED );
	}
	
	@Override
	public ResponseEntity < Boolean > removeRoleFromUser ( UUID userId, String roleName ) {
		
		Optional < User > user = userRepository.findByIdAndIsActiveTrue ( userId );
		Optional < Role > roleEntity = roleRepository
		                                 .findFirstByName ( roleName )
		                                 .stream ( )
		                                 .findFirst ( );
		
		roleEntity
		  .orElseThrow ( )
		  .getUserSet ( )
		  .remove ( user.orElseThrow ( ) );
		
		user
		  .orElseThrow ( )
		  .getRoleSet ( )
		  .remove ( roleEntity.orElseThrow ( ) );
		
		userRepository.save ( user.orElseThrow ( ) );
		roleRepository.save ( roleEntity.orElseThrow ( ) );
		
		return new ResponseEntity <> ( true, HttpStatus.OK );
	}
	
}
