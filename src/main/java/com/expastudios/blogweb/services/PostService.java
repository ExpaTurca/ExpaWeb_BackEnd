/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services;

import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.model.PostDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;



public interface PostService {
	
	ResponseEntity < ? > createPost ( PostDTO postDTO, HttpServletRequest request, HttpServletResponse response );
	
	ResponseEntity < ? > editPost ( PostDTO postDTO, HttpServletRequest request, HttpServletResponse response );
	
	ResponseEntity < ? > removePost ( UUID Id, HttpServletRequest request, HttpServletResponse response );
	
	Optional < Post > getPost ( UUID postId );
	
	Set < Post > getAllPostByUserId ( UUID userId, int pageNumber );
	
}
