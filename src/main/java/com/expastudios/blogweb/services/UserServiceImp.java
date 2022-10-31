/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.*;
import com.expastudios.blogweb.entity.Role;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.model.ProfileDTO;
import com.expastudios.blogweb.model.RegisterDTO;
import com.expastudios.blogweb.repository.RoleRepository;
import com.expastudios.blogweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;



@Slf4j
@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {
    
    @Autowired private UserRepository userRepository;
    
    @Autowired private RoleRepository roleRepository;
    
    
    @Override
    public ResponseEntity < ProfileDTO > getUser ( String email ) {
        
        Optional < ProfileDTO > profileDTO = userRepository
                                               .findByEmail ( email )
                                               .filter ( User::isActive )
                                               .map ( EntityDtoConversion::convertUserToDto );
        
        return new ResponseEntity < ProfileDTO > ( profileDTO.orElseThrow ( ), HttpStatus.OK );
    }
    
    @Override
    public ResponseEntity < ? > saveUser (
      RegisterDTO register, HttpServletRequest request, HttpServletResponse response )
    throws
    Exception {
        
        Thread registerThread = new Thread ( ( ) -> {
            User user = new User ( );
            user.setEmail ( register.getEmail ( ) );
            user.setPassword_hash ( PasswordEncryptor.Encrypt ( register.getPassword ( ) ) );
            user.setFirst_name ( register.getFirstName ( ) );
            user.setLast_name ( register.getLastName ( ) );
            user.setGender ( register.getGender ( ) );
            user.setProfile_image ( register.getProfileImage ( ) );
            user.setActive ( true );
            user.setRegistered_at ( Zone.getCurrentTime ( ) );
            userRepository.save ( user );
        } );
        
        registerThread.start ( );
        registerThread.join ( );
        if ( registerThread.getState ( ) == Thread.State.TERMINATED ) {
            addRoleToUser ( register.getEmail ( ), register.getRoleName ( ) );
        }
        
        return ResponseEntity
                 .ok ( )
                 .build ( );
    }
    
    @Override
    public ResponseEntity < Role > getRole ( String roleName ) {
        
        Optional < Role > role = roleRepository.findOneByRoleName ( roleName );
        return new ResponseEntity < Role > ( role.orElseThrow ( ), HttpStatus.OK );
    }
    
    @Override
    public ResponseEntity < ? > saveRole ( String name ) {
        
        Role roleEntity = new Role ( );
        roleEntity.setRoleName ( name );
        roleRepository.save ( roleEntity );
        return new ResponseEntity <> ( roleEntity, HttpStatus.CREATED );
    }
    
    @Override
    public ResponseEntity < ? > addRoleToUser ( String email, String roleName ) {
        
        Optional < User > user = userRepository.findByEmail ( email );
        Optional < Role > roleEntity = roleRepository
                                         .findOneByRoleName ( roleName )
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
        
        log.info ( "New Role {} added to {}!", roleName, email );
        return new ResponseEntity <> ( HttpStatus.CREATED );
    }
    
    @Override
    public ResponseEntity < ? > removeRoleFromUser ( String email, String roleName ) {
        
        Optional < User > user = userRepository.findByEmail ( email );
        Optional < Role > roleEntity = roleRepository
                                         .findOneByRoleName ( roleName )
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
        return new ResponseEntity <> ( HttpStatus.OK );
    }
    
}
