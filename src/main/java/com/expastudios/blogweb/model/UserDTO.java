/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.model;

import com.expastudios.blogweb.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;




@Getter @Setter @RequiredArgsConstructor
public class UserDTO {
	
	private UUID id;
	
	private String bio;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private String profileImage;
	
	private char gender;
	
	private boolean isActive;
	
	private LocalDateTime registeredAt;
	
	private LocalDateTime lastLogin;
	
	private Set < Role > roleSet;
	
	private Set < Post > postSet;
	
	private Set < Comment > commentSet;
	
}
