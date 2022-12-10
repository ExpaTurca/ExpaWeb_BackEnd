/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.config;

import lombok.*;



@Getter
@Setter
@NoArgsConstructor
public class AuthenticationRequest {
	
	private String email;
	
	private String password;
	
}
