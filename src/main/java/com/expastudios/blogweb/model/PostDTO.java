/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.model;

import com.expastudios.blogweb.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;



@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {
	
	private UUID id;
	
	private String title;
	
	private String description;
	
	private String content;
	
	private LocalDateTime createdAt;
	
	private boolean isActive;
	
	private User author;
	
}
