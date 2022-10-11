package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.entity.Role;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.model.RegisterDTO;
import com.expastudios.blogweb.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.Optional;
import java.util.concurrent.Callable;

@RestController
@RequestMapping("/api")
public class UserEndpoint {
    
    @Autowired
    private UserService userService;
    
    @GetMapping(value = {"", "/home", "/feed"})
    public ResponseEntity home() {
    
        return new ResponseEntity("Index", HttpStatus.OK);
    }
    
    
    @GetMapping(value = "/role/{role}")
    public ResponseEntity<Role> getRole(@PathVariable(value = "role") String roleName) {
        
        return userService.getRole(roleName);
    }
    
    @GetMapping("/user/new")
    public ResponseEntity register() {
        
        return new ResponseEntity("LoginTemplate/registerView", HttpStatus.CREATED);
    }
    
    @PostMapping(value = "/user/new")
    public ResponseEntity<User> register(
      @Valid @RequestBody RegisterDTO register, HttpServletRequest request, HttpServletResponse response
                                        ) throws HttpClientErrorException.BadRequest,
                                                 HttpClientErrorException.Unauthorized, InterruptedException {
        
        return userService.saveUser(register, request, response);
    }
    
    @PostMapping("/role/new")
    public ResponseEntity<Role> addRole(String role) {
        
        return userService.saveRole(role);
    }
    
    @PostMapping("/role/addUser")
    public ResponseEntity<?> addUserToRole(String email, String role) {
        
        return userService.addRoleToUser(email, role);
    }
    
}
