/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.*;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.model.PostDTO;
import com.expastudios.blogweb.repository.PostRepository;
import com.expastudios.blogweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import java.util.*;
import java.util.stream.Collectors;



@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
	
	@Autowired private UserRepository userRepository;
	
	@Autowired private PostRepository postRepository;
	
	public ResponseEntity < ? > createPost ( PostDTO post, HttpServletRequest request, HttpServletResponse response ) {
		
		String email = TokenProvider.getUsernameToken ( request.getHeader ( "Authorization" ) );
		
		try {
			Optional < User > user = userRepository
			                           .findByEmail ( email )
			                           .filter ( User::isActive );
			
			post.setAuthor ( user.orElseThrow ( ) );
			post.setActive ( true );
			post.setCreatedAt ( Zone.getCurrentTime ( ) );
			
			user
			  .map ( ( map ) -> {
				  map
					.getPostSet ( )
					.add ( EntityDtoConversion.convertPostToEntity ( post ) );
				  return map;
			  } )
			  .ifPresent ( ( act ) -> {
				  userRepository.save ( act );
			  } );
			
			postRepository.save ( EntityDtoConversion.convertPostToEntity ( post ) );
			
			return new ResponseEntity <> ( "success-message", HttpStatus.OK );
		} catch ( Exception exc ) {
			throw new RuntimeException ( "Paylaşım oluşturulurken hata oluştu! Hata: " + exc.getMessage ( ) );
		}
	}
	
	@Transactional
	public ResponseEntity < ? > editPost ( PostDTO post, HttpServletRequest request, HttpServletResponse response ) {
		
		try {
			postRepository
			  .findById ( post.getId ( ) )
			  .filter ( Post::isActive )
			  .map ( ( map ) -> {
				  map = EntityDtoConversion.convertPostToEntity ( post );
				  map.setActive ( true );
				  map.setUpdatedAt ( Zone.getCurrentTime ( ) );
				  return map;
			  } )
			  .ifPresent ( ( act ) -> {
				  postRepository.save ( EntityDtoConversion.convertPostToEntity ( post ) );
			  } );
			
			return new ResponseEntity <> ( "success-message", HttpStatus.OK );
		} catch ( Exception exc ) {
			throw new RuntimeException ( exc.getMessage ( ) );
		}
	}
	
	@Transactional
	public ResponseEntity < ? > removePost ( UUID Id, HttpServletRequest request, HttpServletResponse response ) {
		
		try {
			postRepository
			  .findById ( Id )
			  .filter ( Post::isActive )
			  .map ( ( map ) -> {
				  map.setActive ( false );
				  return map;
			  } )
			  .ifPresent ( ( act ) -> {
				  postRepository.save ( act );
			  } );
			
			return new ResponseEntity <> ( "success-message", HttpStatus.OK );
		} catch ( Exception exc ) {
			throw new RuntimeException ( exc.getMessage ( ) );
		}
	}
	
	public Optional < Post > getPost ( UUID postId ) {
		
		return postRepository
		         .findById ( postId )
		         .filter ( Post::isActive );
	}
	
	
	public Set < Post > getAllPostByUserId ( UUID userId, int pageNumber ) {
		
		return userRepository
		         .findById ( userId )
		         .filter ( User::isActive )
		         .map ( ( map ) -> map
			                         .getPostSet ( )
			                         .stream ( )
			                         .filter ( Post::isActive )
			                         .collect ( Collectors.toSet ( ) ) )
		         .orElseThrow ( );
	}
	
}
