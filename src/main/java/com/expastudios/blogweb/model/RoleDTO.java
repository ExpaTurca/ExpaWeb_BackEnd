/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.model;

import lombok.*;

import java.util.UUID;



@Getter
@Setter
@RequiredArgsConstructor
public class RoleDTO {
	
	private UUID userId;
	
	private String roleName;
	
}
