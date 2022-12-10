/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.model;

import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.entity.Role;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;



@RequiredArgsConstructor
@Getter
@Setter
public class UserDTO {
	
	private UUID id;
	
	private String bio;
	
	private String firstName;
	
	private String lastName;
	
	private String email;
	
	private String password;
	
	private char gender;
	
	private String profileImage;
	
	private LocalDateTime registeredAt;
	
	private boolean isActive;
	
	private Set < Role > roleSet;
	
	private Set < Post > postSet = new HashSet <> ( );
	
	/*
	private Set < Comment > commentSet = new HashSet <> ( );
	*/
	
}
