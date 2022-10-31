/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services;

import com.expastudios.blogweb.entity.Role;
import com.expastudios.blogweb.model.ProfileDTO;
import com.expastudios.blogweb.model.RegisterDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public interface UserService {
	
	ResponseEntity < ProfileDTO > getUser ( String email );
	
	ResponseEntity < ? > saveUser (
	  RegisterDTO user, HttpServletRequest request, HttpServletResponse response )
	throws
	Exception;
	
	ResponseEntity < Role > getRole ( String roleName );
	
	
	ResponseEntity < ? > saveRole ( String roleName );
	
	ResponseEntity < ? > addRoleToUser ( String email, String roleName );
	
	ResponseEntity < ? > removeRoleFromUser ( String email, String roleName );
	
}
