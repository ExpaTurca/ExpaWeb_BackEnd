/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.TokenProvider;
import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.*;
import com.expastudios.blogweb.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;



@Service
public class CommentServiceImp implements CommentService {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private PostRepository postRepository;
	
	@Autowired private CommentRepository commentRepository;
	
	// // // // // // // // // // //
	//  CRUD Functions //
	// // // // // // // // // // //
	
	@Override
	public Optional < Comment > getComment ( UUID commentID ) {
		
		return commentRepository
		         .findById ( commentID )
		         .filter ( Comment::isActive );
	}
	
	public Set < Comment > getAllByAuthorId ( UUID authorID ) {
		
		return userRepository
		         .findById ( authorID )
		         .filter ( User::isActive )
		         .orElseThrow ( )
		         .getCommentSet ( )
		         .stream ( )
		         .filter ( Comment::isActive )
		         .collect ( Collectors.toSet ( ) );
	}
	
	public Set < Comment > getAllByPostID ( UUID postID ) {
		
		return postRepository
		         .findById ( postID )
		         .filter ( Post::isActive )
		         .orElseThrow ( )
		         .getCommentSet ( )
		         .stream ( )
		         .filter ( Comment::isActive )
		         .collect ( Collectors.toSet ( ) );
	}
	
	@Override
	public ResponseEntity < ? > create (
	  UUID postID, Comment comment, HttpServletRequest request, HttpServletResponse response ) {
		
		String email = TokenProvider.getUsernameToken ( request.getHeader ( "Authorization" ) );
		
		try {
			comment.setActive ( true );
			comment.setCreatedAt ( Zone.getCurrentTime ( ) );
			
			//Yorumu yapan kullanıcının repositorisi
			userRepository
			  .findByEmail ( email )
			  .filter ( User::isActive )
			  .map ( ( map ) -> {
				  map
					.getCommentSet ( )
					.add ( comment );
				  return map;
			  } )
			  .ifPresent ( ( act ) -> {
				  userRepository.save ( act );
			  } );
			
			//Yorum yapılan paylaşımın repositorisi
			postRepository
			  .findById ( postID )
			  .filter ( Post::isActive )
			  .map ( ( map ) -> {
				  map
					.getCommentSet ( )
					.add ( comment );
				  return map;
			  } )
			  .ifPresent ( ( act ) -> {
				  postRepository.save ( act );
			  } );
			
			return new ResponseEntity <> ( "success-message", HttpStatus.CREATED );
		} catch ( Exception exc ) {
			throw new RuntimeException ( exc.getMessage ( ) );
		}
	}
	
	@Override
	public ResponseEntity < ? > edit ( Comment comment ) {
		
		try {
			comment.setActive ( true );
			comment.setUpdatedAt ( Zone.getCurrentTime ( ) );
			commentRepository
			  .findById ( comment.getId ( ) )
			  .filter ( Comment::isActive );
			
			commentRepository.save ( comment );
			
			return new ResponseEntity <> ( "success-message", HttpStatus.OK );
		} catch ( Exception exc ) {
			exc.printStackTrace ( );
			throw new RuntimeException ( exc.getMessage ( ) );
		}
	}
	
	@Override
	@Transactional
	public ResponseEntity < ? > delete ( UUID Id ) {
		
		try {
			Comment comment = commentRepository
			                    .findById ( Id )
			                    .filter ( Comment::isActive )
			                    .map ( ( map ) -> {
				                    map.setActive ( false );
				                    return map;
			                    } )
			                    .orElseThrow ( );
			commentRepository.save ( comment );
			
			return new ResponseEntity <> ( "success-message", HttpStatus.OK );
		} catch ( Exception exc ) {
			throw new RuntimeException ( exc.getMessage ( ) );
		}
	}
	
}
