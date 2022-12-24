/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services.IServices;

import com.expastudios.blogweb.entity.DTOs.UserDTO;
import com.expastudios.blogweb.entity.Forms.NewUserForm;
import com.expastudios.blogweb.entity.User;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public interface UserService {

    ResponseEntity<UserDTO> getUser(String email);

    ResponseEntity<?> saveUser(
            NewUserForm userForm, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> editUser(User userEntity, HttpServletRequest request, HttpServletResponse response);

}
