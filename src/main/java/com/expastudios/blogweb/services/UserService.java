package com.expastudios.blogweb.services;

import com.expastudios.blogweb.entity.Role;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.model.ProfileDTO;
import com.expastudios.blogweb.model.RegisterDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



public interface UserService {

    ResponseEntity<ProfileDTO> getUser(String email);

    ResponseEntity<User> saveUser(RegisterDTO user, HttpServletRequest request, HttpServletResponse response) throws InterruptedException;

    ResponseEntity<Role> getRole(String roleName);


    ResponseEntity<Role> saveRole(String roleName);

    ResponseEntity<Boolean> addRoleToUser(String email, String roleName);

    ResponseEntity<Boolean> removeRoleFromUser(String email, String roleName);

}
