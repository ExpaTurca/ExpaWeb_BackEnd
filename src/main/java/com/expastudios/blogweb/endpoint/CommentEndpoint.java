/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.Util.EntityDtoConversion;
import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.model.CommentDTO;
import com.expastudios.blogweb.services.CommentServiceImp;
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
@RequestMapping ( "/api/comment" )
public class CommentEndpoint {
	
	@Autowired private CommentServiceImp commentServiceImp;
	
	@GetMapping
	public Optional < CommentDTO > getComment (
	  @RequestParam ( value = "c", required = true ) UUID id ) {
		
		return Optional.of ( EntityDtoConversion.convertCommentToDto ( commentServiceImp
		                                                                 .getComment ( id )
		                                                                 .stream ( )
		                                                                 .iterator ( )
		                                                                 .next ( ) ) );
	}
	
	@GetMapping ( "/u/list" )
	public Set < CommentDTO > getAllByAuthor (
	  @RequestParam ( value = "author", required = true ) UUID authorId,
	  @RequestParam ( value = "pg" ) int pageNumber ) {
		
		return Optional
		         .of ( EntityDtoConversion.convertCommentToDto ( commentServiceImp
			                                                       .getAllByAuthorId ( authorId )
			                                                       .iterator ( )
			                                                       .next ( ) ) )
		         .stream ( )
		         .collect ( Collectors.toSet ( ) );
	}
	
	@GetMapping ( "/p/list" )
	public Set < CommentDTO > getAllByPost (
	  @RequestParam ( value = "pID", required = true ) UUID postId,
	  @RequestParam ( value = "pg" ) int pageNumber ) {
		
		return Optional
		         .of ( EntityDtoConversion.convertCommentToDto ( commentServiceImp
			                                                       .getAllByPostID ( postId )
			                                                       .iterator ( )
			                                                       .next ( ) ) )
		         .stream ( )
		         .collect ( Collectors.toSet ( ) );
	}
	
	@PostMapping ( "/new" )
	public ResponseEntity < ? > createComment (
	  @RequestBody CommentDTO commentDTO,
	  @RequestParam ( value = "p", required = true ) String postId, HttpServletRequest request,
	  HttpServletResponse response ) {
		
		return commentServiceImp.create ( UUID.fromString ( postId ),
		                                  EntityDtoConversion.convertCommentToEntity ( commentDTO ), request,
		                                  response );
	}
	
	@PostMapping ( "/edit" )
	public ResponseEntity < ? > editComment (
	  @RequestBody CommentDTO commentDTO,
	  @RequestParam ( value = "c" ) String commentId, HttpServletRequest request, HttpServletResponse response ) {
		
		commentDTO.setId ( UUID.fromString ( commentId ) );
		
		Comment comment = EntityDtoConversion.convertCommentToEntity ( commentDTO );
		return commentServiceImp.edit ( comment );
	}
	
	@PostMapping ( "/remove" )
	public ResponseEntity < ? > removeComment (
	  @RequestParam ( value = "c" ) String Id ) {
		
		return commentServiceImp.delete ( UUID.fromString ( Id ) );
	}
	
}
