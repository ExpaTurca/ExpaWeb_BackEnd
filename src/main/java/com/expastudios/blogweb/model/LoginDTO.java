/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class LoginDTO {
	
	private String login;
	
	private String password;
	
}
