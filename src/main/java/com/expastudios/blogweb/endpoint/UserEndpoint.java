/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.model.RoleDTO;
import com.expastudios.blogweb.model.UserDTO;
import com.expastudios.blogweb.services.IServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;



@RestController
@RequestMapping ( "/api")
@RequiredArgsConstructor
public class UserEndpoint {
    
    @Autowired private final UserService userService;
    
    @GetMapping ( "/user" )
    public ResponseEntity < ? > Index ( String email, HttpServletRequest request, HttpServletResponse response )
    throws
    ClassNotFoundException {
    
        return userService.getUser ( email );
    }
    
    @PostMapping ( "/user/create" )
    public ResponseEntity < ? > NewUser (
      @RequestBody UserDTO userDTO, HttpServletRequest request, HttpServletResponse response )
    throws
    ClassNotFoundException {
    
        return userService.saveUser ( userDTO, request, response );
    }
    
    public ResponseEntity < ? > EditUser (
      @RequestBody UserDTO userDTO, HttpServletRequest request, HttpServletResponse response )
    throws
    ClassNotFoundException {
    
        return userService.editUser ( userDTO, request, response );
    }
    
    @PostMapping ( "/role/create" )
    public ResponseEntity < ? > NewRole ( String roleName, HttpServletRequest request, HttpServletResponse response ) {
    
        return userService.saveRole ( roleName );
    }
    
    @PostMapping ( "/role/adduser" )
    public ResponseEntity < ? > AddRoleToUser (
      @RequestBody RoleDTO roleDTO ) {
    
        UUID userId = roleDTO.getUserId ( );
    
        String roleName = roleDTO.getRoleName ( );
        return userService.addRoleToUser ( userId, roleName );
    }
    
    public ResponseEntity < ? > RemoveRole ( String roleName ) {
    
        return userService.deleteRole ( roleName );
    }
    
    
    @PostMapping ( "/role/user/delete" )
    public ResponseEntity < ? > RemoveRoleFromUser ( String userId, String roleName ) {
    
        return userService.removeRoleFromUser ( UUID.fromString ( userId ), roleName );
    }
    
}
