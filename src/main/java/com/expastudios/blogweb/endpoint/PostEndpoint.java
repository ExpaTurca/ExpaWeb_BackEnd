/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.Util.EntityDtoConversion;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.model.PostDTO;
import com.expastudios.blogweb.services.IServices.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;
import java.util.stream.Collectors;



@Slf4j
@RestController
@RequestMapping ( "/api")
public class PostEndpoint {
	
	@Autowired private PostService postService;
	
	@GetMapping ( "/post?postId={Id}" )
	public Optional < PostDTO > getPost (
	  @PathVariable ( value = "Id" ) String postId )
	throws
	ClassNotFoundException {
		
		return Optional.of (
		  ( PostDTO ) EntityDtoConversion.ConvertToDTO ( postService.getPost ( UUID.fromString ( postId ) ) ) );
	}
	
	@GetMapping ( "/post/list" )
	@ResponseBody
	public Set < PostDTO > getAllPost (
	  @PathVariable ( value = "userID", required = true ) String userId,
	  @PathVariable ( value = "page", required = false ) int pageNumber )
	throws
	ClassNotFoundException {
		
		return postService
		         .getPostByUser ( UUID.fromString ( userId ), pageNumber )
		         .stream ( )
		         .map ( map -> {
			         try {
				         return ( PostDTO ) EntityDtoConversion.ConvertToDTO ( map );
			         } catch ( ClassNotFoundException e ) {
				         throw new RuntimeException ( e );
			         }
		         } )
		         .collect ( Collectors.toSet ( ) );
	}
	
	@PostMapping (value = "/post/create" )
	public ResponseEntity < ? > createPost (
	  @RequestBody PostDTO postDTO, HttpServletRequest request, HttpServletResponse response )
	throws
	ClassNotFoundException {
		
		return postService.createPost ( ( Post ) EntityDtoConversion.ConvertToEntity ( postDTO ), request,
		                                response );
	}
	
	@PostMapping ( value = "/post/edit" )
	public ResponseEntity < ? > editPost (
	  @RequestBody PostDTO postDTO, HttpServletRequest request, HttpServletResponse response )
	throws
	ClassNotFoundException {
		
		return postService.editPost ( ( Post ) EntityDtoConversion.ConvertToEntity ( postDTO ), request, response );
	}
	
	@PostMapping ( "/delete" )
	public ResponseEntity < ? > removePost (
	  @PathVariable ( "post" ) UUID id, HttpServletRequest request, HttpServletResponse response ) {
		
		return postService.removePost ( id, request, response );
	}
	
}
