/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.TokenProvider;
import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.repository.PostRepository;
import com.expastudios.blogweb.repository.UserRepository;
import com.expastudios.blogweb.services.IServices.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.*;



@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
	
	@Autowired private final UserRepository userRepository;
	
	@Autowired private final PostRepository postRepository;
	
	@Override
	public Optional < Post > getPost ( UUID postId ) {
		
		return postRepository
		         .findByIdAndIsActiveTrue ( postId );
	}
	
	@Override
	public Set<Post> getPostByUser ( UUID userId, int pageNumber ) {
		
		return userRepository
		         .findByIdAndIsActiveTrue ( userId )
		         .map ( User::getPostSet )
		         .orElseThrow ( );
	}
	
	@Override
	public ResponseEntity < Boolean > createPost (
	  Post post, HttpServletRequest request, HttpServletResponse response ) {
		
		String email = TokenProvider.getUsernameWithToken ( request.getHeader ( "Authorization" ) );
		
		try {
			Optional < User > user = userRepository.findByEmailAndIsActiveTrue ( email );
			
			post.setActive ( true );
			post.setCreatedAt ( Zone.getCurrentTime ( ) );
			user
			  .map ( ( map ) -> {
				  map
				    .getPostSet ( )
				    .add ( post );
				  return map;
			  } )
			  .ifPresent ( userRepository::save );
			
			return new ResponseEntity <> ( true, HttpStatus.OK );
		} catch ( Exception exc ) {
			throw new RuntimeException ( exc.getLocalizedMessage ( ) );
		}
	}
	
	@Override
	public ResponseEntity < Boolean > editPost (
	  Post post, HttpServletRequest request, HttpServletResponse response ) {
		
		postRepository
		  .findByIdAndIsActiveTrue ( post.getId ( ) )
		  .map ( ( map ) -> {
			  map.setActive ( true );
			  map.setLastUpdatedAt ( Zone.getCurrentTime ( ) );
			  return map;
		  } )
		  .ifPresent ( ( act ) -> {
			  postRepository.save ( post );
		  } );
		
		return new ResponseEntity <> ( true, HttpStatus.OK );
	}
	
	@Override
	public ResponseEntity < Boolean > removePost (
	  UUID Id, HttpServletRequest request, HttpServletResponse response ) {
		postRepository
		  .findByIdAndIsActiveTrue ( Id )
		  .map ( ( map ) -> {
			  map.setActive ( false );
			  return map;
		  } )
		  .ifPresent ( postRepository::save );
		
		return new ResponseEntity <> ( true, HttpStatus.OK );
	}
	
}
