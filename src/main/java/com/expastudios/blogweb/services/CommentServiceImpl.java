/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.TokenProvider;
import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.repository.*;
import com.expastudios.blogweb.services.IServices.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;



@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
	
	@Autowired private final UserRepository userRepository;
	            
	@Autowired private final PostRepository postRepository;
	
	@Autowired private final CommentRepository commentRepository;
	
	@Override
	public Optional < Comment > getComment ( UUID commentId ) {
		
		return commentRepository.findByIdAndIsActiveTrue ( commentId );
	}
	
	@Override
	public Set < Comment > getCommentByPost ( UUID postId, int pageNumber ) {
		
		return postRepository
		         .findByIdAndIsActiveTrue ( postId )
		         .map ( Post::getCommentSet )
		         .orElseThrow ( );
	}
	
	@Override
	public ResponseEntity < Boolean > createComment (
	  UUID postId, Comment commentEntity, HttpServletRequest request, HttpServletResponse response ) {
		
		try {
			String email = TokenProvider.getUsernameWithToken ( request.getHeader ( "Authorization" ) );
			
			commentEntity.setActive ( true );
			commentEntity.setCreatedAt ( Zone.getCurrentTime ( ) );
			
			userRepository
			  .findByEmailAndIsActiveTrue ( email )
			  .map ( ( map ) -> {
				  map
					.getCommentSet ( )
					.add ( commentEntity );
				  return map;
			  } )
			  .ifPresent ( userRepository::save );
			
			postRepository
			  .findByIdAndIsActiveTrue ( postId )
			  .map ( map -> {
				  map
				    .getCommentSet ( )
				    .add ( commentEntity );
				  return map;
			  } )
			  .ifPresent ( postRepository::save );
			
			return ResponseEntity.ok ( true);
		} catch ( Exception exc ) {
			throw new RuntimeException ( exc.getLocalizedMessage ( ) );
		}
	}
	
	@Override
	public ResponseEntity < Boolean > editComment (
	  UUID commentId, Comment commentEntity, HttpServletRequest request, HttpServletResponse response ) {
		
		commentEntity.setActive ( true );
		commentEntity.setLastUpdatedAt ( Zone.getCurrentTime ( ) );
		
		commentRepository
		  .findByIdAndIsActiveTrue ( commentId )
		  .ifPresent ( commentRepository::save );
		
		return ResponseEntity.ok ( true );
	}
	
	@Override
	public ResponseEntity < Boolean > removeComment (
	  UUID commentId, HttpServletRequest request, HttpServletResponse response ) {
		
		commentRepository
		  .findByIdAndIsActiveTrue ( commentId )
		  .map ( ( map ) -> {
			  map.setActive ( false );
			  return map;
		  } )
		  .ifPresent ( commentRepository::save );
		return ResponseEntity.ok ( true );
	}
	
}
