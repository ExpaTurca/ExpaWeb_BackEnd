/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.entity.Role;
import com.expastudios.blogweb.model.ProfileDTO;
import com.expastudios.blogweb.model.RegisterDTO;
import com.expastudios.blogweb.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;



@Slf4j
@RestController
@RequestMapping ( "/api" )
public class UserEndpoint {
    
    @Autowired private UserService userService;
    
    @GetMapping ( value = { "", "/home", "/feed" } )
    public ResponseEntity < ? > home ( ) {
        
        return new ResponseEntity <> ( "Index", HttpStatus.OK );
    }
    
    
    @GetMapping ( value = "/role" )
    public ResponseEntity < Role > getRole (
      @RequestParam ( value = "role" ) String roleName ) {
        
        return userService.getRole ( roleName );
    }
    
    
    @GetMapping ( "/user" )
    public ResponseEntity < ProfileDTO > getUser (
      @RequestParam ( value = "email", required = true ) String email ) {
        
        return userService.getUser ( email );
    }
    
    @PostMapping ( value = "/user/new" )
    public ResponseEntity < ? > register (
      @Valid
      @RequestBody
      RegisterDTO register, HttpServletRequest request, HttpServletResponse response )
    throws
    Exception {
        
        return userService.saveUser ( register, request, response );
    }
    
    @PostMapping ( "/role/new" )
    public ResponseEntity < ? > addRole ( String role ) {
        
        return userService.saveRole ( role );
    }
    
    @PostMapping ( "/role/adduser" )
    public ResponseEntity < ? > addUserToRole ( String email, String role ) {
        
        return userService.addRoleToUser ( email, role );
    }
    
}
