/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services.IServices;

import com.expastudios.blogweb.entity.Post;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;



public interface PostService {
	
	Optional < Post > getPost ( UUID postId );
	
	Set < Post > getPostByUser ( UUID userId, int pageNumber )
	throws
	ClassNotFoundException;
	
	ResponseEntity < ? > createPost ( Post postEntity, HttpServletRequest request, HttpServletResponse response );
	
	ResponseEntity < ? > editPost ( Post postEntity, HttpServletRequest request, HttpServletResponse response );
	
	ResponseEntity < ? > removePost ( UUID Id, HttpServletRequest request, HttpServletResponse response );
	
}
