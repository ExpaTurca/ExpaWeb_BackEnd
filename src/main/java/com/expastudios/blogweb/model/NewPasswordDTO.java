/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.model;

import lombok.*;

@Getter
@Setter
@RequiredArgsConstructor
public class NewPasswordDTO {
	
	private String current_password;
	
	private String new_password;
	
}
