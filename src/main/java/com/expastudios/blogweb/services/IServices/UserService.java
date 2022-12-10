/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services.IServices;

import com.expastudios.blogweb.entity.Role;
import com.expastudios.blogweb.model.UserDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.UUID;



public interface UserService {
	
	ResponseEntity < UserDTO > getUser ( String email );
	
	ResponseEntity < ? > saveUser (
	  UserDTO user, HttpServletRequest request, HttpServletResponse response )
	throws
	ClassNotFoundException;
	
	ResponseEntity < ? > editUser ( UserDTO userDTO, HttpServletRequest request, HttpServletResponse response )
	throws
	ClassNotFoundException;
	
	ResponseEntity < Role > getRole ( String roleName );
	
	
	ResponseEntity < ? > saveRole ( String roleName );
	
	ResponseEntity < ? > deleteRole ( String roleName );
	
	ResponseEntity < ? > addRoleToUser ( UUID userId, String roleName );
	
	ResponseEntity < ? > removeRoleFromUser ( UUID userId, String roleName );
	
	
}
