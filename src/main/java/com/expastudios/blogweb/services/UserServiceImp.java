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
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;



@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private RoleRepository roleRepository;
	
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
	  UserDTO userDto, HttpServletRequest request, HttpServletResponse response )
	throws
	ClassNotFoundException {
		
		try {
			User user = ( User ) EntityDtoConversion.ConvertToEntity ( userDto );
			user.setPassword ( PasswordEncryptor.Encrypt ( user.getPassword ( ) ) );
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
	public ResponseEntity < ? > editUser ( UserDTO userDTO, HttpServletRequest request, HttpServletResponse response )
	throws
	ClassNotFoundException {
		
		try {
			User user = ( User ) EntityDtoConversion.ConvertToEntity ( userDTO );
			userRepository.save ( user );
		} catch ( HibernateException e ) {
			throw new RuntimeException ( e );
		}
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
	public ResponseEntity < ? > saveRole ( String name ) {
		
		Role roleEntity = new Role ( );
		roleEntity.setName ( name );
		roleRepository.save ( roleEntity );
		return new ResponseEntity <> ( "success-message", HttpStatus.CREATED );
	}
	
	@Override
	public ResponseEntity < ? > deleteRole ( String name ) {
		
		try {
			Role roleEntity = roleRepository
			                    .findFirstByName ( name )
			                    .orElseThrow ( );
			roleRepository.delete ( roleEntity );
		} catch ( HibernateException e ) {
			throw new RuntimeException ( e );
		}
		
		return new ResponseEntity <> ( "success-message", HttpStatus.OK );
	}
	
	@Override
	public ResponseEntity < ? > addRoleToUser ( UUID userId, String roleName ) {
		
		Optional < User > user = userRepository.findByIdAndIsActiveTrue ( userId );
		Optional < Role > roleEntity = roleRepository
		                                 .findFirstByName ( roleName )
		                                 .stream ( )
		                                 .findFirst ( );
		
		roleEntity
		  .orElseThrow ( )
		  .getUsers ( )
		  .add ( user.orElseThrow ( ) );
		
		user
		  .orElseThrow ( )
		  .getRoles ( )
		  .add ( roleEntity.orElseThrow ( ) );
		
		userRepository.save ( user.orElseThrow ( ) );
		roleRepository.save ( roleEntity.orElseThrow ( ) );
		
		log.info ( "New Role {} added to user!", roleName );
		return new ResponseEntity <> ( "success-message", HttpStatus.CREATED );
	}
	
	@Override
	public ResponseEntity < ? > removeRoleFromUser ( UUID userId, String roleName ) {
		
		Optional < User > user = userRepository.findByIdAndIsActiveTrue ( userId );
		Optional < Role > roleEntity = roleRepository
		                                 .findFirstByName ( roleName )
		                                 .stream ( )
		                                 .findFirst ( );
		
		roleEntity
		  .orElseThrow ( )
		  .getUsers ( )
		  .remove ( user.orElseThrow ( ) );
		
		user
		  .orElseThrow ( )
		  .getRoles ( )
		  .remove ( roleEntity.orElseThrow ( ) );
		
		userRepository.save ( user.orElseThrow ( ) );
		roleRepository.save ( roleEntity.orElseThrow ( ) );
		
		return new ResponseEntity <> ( "success-message", HttpStatus.OK );
	}
	
}
