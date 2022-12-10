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
import java.util.stream.Collectors;



@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
	
	private final UserRepository userRepository;
	
	@Autowired private PostRepository postRepository;
	
	@Override
	public Optional < PostDTO > getPost ( UUID postId ) {
		
		return postRepository
		         .findById ( postId )
		         .filter ( Post::isActive )
		         .map ( ( map ) -> {
			         try {
				         return ( PostDTO ) EntityDtoConversion.ConvertToDTO ( map );
			         } catch ( ClassNotFoundException e ) {
				         throw new RuntimeException ( e );
			         }
		         } );
	}
	
	@Override
	public Set<PostDTO> getPostByUser ( UUID userId, int pageNumber ) {
		
		return postRepository
		         .findByAuthor_Id ( userId )
		         .stream ( )
		         .filter ( Post::isActive )
		         .map ( ( map ) -> {
			         try {
				         return ( PostDTO ) EntityDtoConversion.ConvertToDTO ( map );
			         } catch ( ClassNotFoundException e ) {
				         throw new RuntimeException ( e );
			         }
		         } )
		         .collect ( Collectors.toSet ( ) );
	}
	
	@Override
	public ResponseEntity < ? > createPost (
	  PostDTO post, HttpServletRequest request, HttpServletResponse response ) {
		
		String email = TokenProvider.getUsernameWithToken ( request.getHeader ( "Authorization" ) );
		
		try {
			Optional < User > user = userRepository.findByEmailAndIsActiveTrue ( email );
			
			post.setAuthor ( user.orElseThrow ( ) );
			post.setActive ( true );
			post.setCreatedAt ( Zone.getCurrentTime ( ) );
			
			user
			  .map ( ( map ) -> {
				  try {
					  map
					    .getPostSet ( )
					    .add ( ( Post ) EntityDtoConversion.ConvertToEntity ( post ) );
					
				  } catch ( ClassNotFoundException e ) {
					  throw new RuntimeException ( e );
				  }
				  return map;
			  } )
			  .ifPresent ( ( act ) -> {
				  userRepository.save ( act );
				  post.setAuthor ( user.orElseThrow ( ) );
			  } );
			
			return new ResponseEntity <> ( "success-message", HttpStatus.OK );
		} catch ( Exception exc ) {
			throw new RuntimeException ( exc.getLocalizedMessage ( ) );
		}
	}
	
	@Override
	public ResponseEntity < ? > editPost (
	  PostDTO post, HttpServletRequest request, HttpServletResponse response ) {
		
		return new ResponseEntity <> ( HttpStatus.OK );
/*		try {
			postRepository
			  .findOneById ( post.getId ( ) )
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
			
			return new ResponseEntity <> ( "true", HttpStatus.OK );
		} catch ( Exception exc ) {
			throw new RuntimeException ( "Hata: " + exc.getMessage ( ) );
		}*/
	}
	
	@Override
	public ResponseEntity < ? > removePost (
	  UUID Id, HttpServletRequest request, HttpServletResponse response ) {
		
		return  new ResponseEntity<> ( HttpStatus.OK );
/*		try {
			postRepository
			  .findOneById ( Id )
			  .filter ( Post::isActive )
			  .map ( ( map ) -> {
				  map.setActive ( false );
				  return map;
			  } )
			  .ifPresent ( ( act ) -> {
				  postRepository.save ( act );
			  } );
			
			return new ResponseEntity <> ( "true", HttpStatus.OK );
		} catch ( Exception exc ) {
			throw new RuntimeException ( exc.getMessage ( ) );
		}*/
	}
	
}
