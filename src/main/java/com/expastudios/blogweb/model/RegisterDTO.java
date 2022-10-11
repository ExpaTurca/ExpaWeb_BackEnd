package com.expastudios.blogweb.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class RegisterDTO {
	
	private String email;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private char   gender;
	
	private String profileImage;
	
	private String roleName;
	
}
