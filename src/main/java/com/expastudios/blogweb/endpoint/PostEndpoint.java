/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.Util.EntityDtoConversion;
import com.expastudios.blogweb.model.PostDTO;
import com.expastudios.blogweb.services.PostServiceImp;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;



@Slf4j
@RestController
@RequestMapping ( "/api/post" )
public class PostEndpoint {
	
	@Autowired private PostServiceImp postServiceImp;
	
	@GetMapping ( "?p={Id}" )
	public Optional < PostDTO > getPost (
	  @PathVariable ( value = "Id" ) UUID postId ) {
		
		
		return postServiceImp
		         .getPost ( postId )
		         .map ( EntityDtoConversion::convertPostToDto );
	}
	
	@GetMapping ( "/list" )
	@ResponseBody
	public Collection < PostDTO > getAllPost (
	  @RequestParam ( value = "uID", required = true ) String userId,
	  @RequestParam ( value = "pg", required = false ) int pageNumber ) {
		
		if ( pageNumber == 0 ) { pageNumber = 1; }
		Collection < PostDTO > postDTOCollection = new ArrayList <> ( );
		postServiceImp
		  .getAllPostByUserId ( UUID.fromString ( userId ), pageNumber )
		  .forEach ( ( pst ) -> {
			  postDTOCollection.add ( EntityDtoConversion.convertPostToDto ( pst ) );
		  } );
		return postDTOCollection;
	}
	
	@PostMapping ( value = "/new" )
	public ResponseEntity < ? > createPost (
	  @RequestBody PostDTO postDTO, HttpServletRequest request, HttpServletResponse response ) {
		
		return postServiceImp.createPost ( postDTO, request, response );
	}
	
	@PostMapping ( "/edit" )
	public ResponseEntity < ? > editPost (
	  @RequestBody PostDTO postDTO,
	  @RequestParam ( value = "p", required = true ) String postId, HttpServletRequest request,
	  HttpServletResponse response ) {
		
		postDTO.setId ( UUID.fromString ( postId ) );
		return postServiceImp.editPost ( postDTO, request, response );
	}
	
	@PostMapping ( "/remove" )
	public ResponseEntity < ? > removePost (
	  @RequestParam ( value = "p", required = true ) UUID id, HttpServletRequest request,
	  HttpServletResponse response ) {
		
		return postServiceImp.removePost ( id, request, response );
	}
	
}
