/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.model;

import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.entity.User;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;



@Getter
@Setter
@RequiredArgsConstructor
public class CommentDTO {
	
	private UUID id;
	
	private User author;
	
	private Post post;
	
	private String content;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private boolean isActive;
	
}
