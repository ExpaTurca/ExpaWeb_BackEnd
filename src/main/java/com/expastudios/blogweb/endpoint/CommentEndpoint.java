/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.Util.EntityDtoConversion;
import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.model.CommentDTO;
import com.expastudios.blogweb.services.IServices.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.UUID;



@RestController
@RequestMapping(value = "/api")
@RequiredArgsConstructor
public class CommentEndpoint {
	
	@Autowired private final CommentService commentService;
	
	@GetMapping ( value = "/comment/get" )
	public Optional < CommentDTO > getComment ( UUID commentId )
	throws
	ClassNotFoundException {
		
		return Optional.of (
		  ( CommentDTO ) EntityDtoConversion.ConvertToDTO ( commentService.getComment ( commentId ) ) );
	}
	
	@PostMapping ( value = "/comment/create" )
	public ResponseEntity < ? > createComment (
	  @PathVariable ( value = "postid" ) String postId, @RequestBody CommentDTO commentDTO, HttpServletRequest request,
	  HttpServletResponse response )
	throws
	ClassNotFoundException {
		
		System.out.println ("meraba" );
		return commentService.createComment ( UUID.fromString (postId),
		                                      ( Comment ) EntityDtoConversion.ConvertToEntity ( commentDTO ), request, response );
	}
	
	public ResponseEntity < Boolean > removeComment (UUID commentId, HttpServletRequest request,
	                                           HttpServletResponse response ) {
		
		return commentService.removeComment ( commentId, request, response );
	}
	
}
