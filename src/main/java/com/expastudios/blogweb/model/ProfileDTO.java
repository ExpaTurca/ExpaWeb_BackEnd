/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.model;

import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.entity.Post;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;



@RequiredArgsConstructor
@Getter
@Setter
public class ProfileDTO {
	
	private UUID id;
	
	private String first_name;
	
	private String last_name;
	
	private String email;
	
	private String password_hash;
	
	private char gender;
	
	private String profile_image;
	
	private LocalDateTime registered_at;
	
	private boolean is_active;
	
	private Set < Post > postSet = new HashSet <> ( );
	
	private Set < Comment > commentSet = new HashSet <> ( );
	
}
