/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services;

import com.expastudios.blogweb.entity.Comment;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;



public interface CommentService {
	
	Optional < Comment > getComment ( UUID commentID );
	
	ResponseEntity < ? > create (
	  UUID postID, Comment comment, HttpServletRequest request, HttpServletResponse response );
	
	ResponseEntity < ? > edit ( Comment comment );
	
	ResponseEntity < ? > delete ( UUID commentID );
	
}
