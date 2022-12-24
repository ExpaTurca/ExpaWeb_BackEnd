/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.Util.EntityDtoConversion;
import com.expastudios.blogweb.entity.DTOs.UserDTO;
import com.expastudios.blogweb.entity.Forms.NewUserForm;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.services.IServices.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@RestController
@RequestMapping(value = "/api", consumes = {"application/xml", "application/json"})
@RequiredArgsConstructor
public class UserEndpoint {

    @Autowired
    private final UserService userService;

    @GetMapping(value = "/user")
    public ResponseEntity<?> Index(@RequestParam String username, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {

        return userService.getUser(username);
    }

    @PostMapping(value = "/user/create")
    public ResponseEntity<?> NewUser(@RequestBody NewUserForm userForm, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {

        return userService.saveUser(userForm, request, response);
    }

    @PostMapping(value = "/user/edit")
    public ResponseEntity<?> EditUser(@RequestBody UserDTO userDTO, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {

        return userService.editUser((User) EntityDtoConversion.ConvertToEntity(userDTO), request, response);
    }
}