/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services.IServices;

import com.expastudios.blogweb.entity.Comment;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;



public interface CommentService {
	
	Optional < Comment > getComment ( UUID commentId );
	
	Set < Comment > getCommentByPost ( UUID postId, int pageNumber );
	
	ResponseEntity < ? > createComment (
	  UUID postId, Comment commentEntity, HttpServletRequest request, HttpServletResponse response );
	
	ResponseEntity < ? > editComment (
	  UUID commentId, Comment commentEntity, HttpServletRequest request, HttpServletResponse response );

	ResponseEntity<?> removeComment(UUID Id, HttpServletRequest request, HttpServletResponse response);
	
}
