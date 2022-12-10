/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services.IServices;

import com.expastudios.blogweb.model.PostDTO;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;



public interface PostService {
	
	ResponseEntity < ? > createPost ( PostDTO postDTO, HttpServletRequest request, HttpServletResponse response );
	
	ResponseEntity < ? > editPost ( PostDTO postDTO, HttpServletRequest request, HttpServletResponse response );
	
	ResponseEntity < ? > removePost ( UUID Id, HttpServletRequest request, HttpServletResponse response );
	
	Optional < PostDTO > getPost ( UUID postId );
	
	Set < PostDTO > getPostByUser ( UUID userId, int pageNumber );
	
}
