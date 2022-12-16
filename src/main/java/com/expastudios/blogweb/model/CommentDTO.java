/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.model;

import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;



@Getter @Setter @RequiredArgsConstructor
public class CommentDTO {
	
	private UUID id;
	
	private String content;
	
	private boolean isActive;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime lastUpdatedAt;
	
	
}
