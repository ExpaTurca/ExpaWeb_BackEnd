/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.model;

import com.expastudios.blogweb.entity.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.*;



@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {
	
	private UUID id;
	
	private User author;
	
	private String meta;
	
	private String summary;
	
	private String content;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	private Set < Comment > commentSet = new HashSet <> ( );
	
	private Set < Tag > tagSet = new HashSet <> ( );
	
	private boolean isActive;
	
}
