/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.model;

import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.entity.Tag;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;



@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {
	
	private UUID id;
	
	private String title;
	
	private String description;
	
	private String content;
	
	private boolean isActive;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime lastUpdatedAt;
		
	private Set < Comment > commentSet;
	
	private Set < Tag > tagSet;
	
}
