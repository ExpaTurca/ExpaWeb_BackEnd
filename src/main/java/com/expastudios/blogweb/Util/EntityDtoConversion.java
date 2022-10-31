/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.Util;

import com.expastudios.blogweb.entity.*;
import com.expastudios.blogweb.model.*;
import lombok.*;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;



@Getter
@Setter
@RequiredArgsConstructor
public class EntityDtoConversion {
	
	@Autowired private static final ModelMapper modelMapper = new ModelMapper ( );
	
	public static ProfileDTO convertUserToDto ( User user ) {
		
		ProfileDTO profileDTO = modelMapper.map ( user, ProfileDTO.class );
		
		profileDTO.setId ( user.getId ( ) );
		profileDTO.setFirst_name ( user.getFirst_name ( ) );
		profileDTO.setLast_name ( user.getLast_name ( ) );
		profileDTO.setEmail ( user.getEmail ( ) );
		profileDTO.setPassword_hash ( user.getPassword_hash ( ) );
		profileDTO.setProfile_image ( user.getProfile_image ( ) );
		profileDTO.setGender ( user.getGender ( ) );
		profileDTO.setPostSet ( user.getPostSet ( ) );
		profileDTO.setCommentSet ( user.getCommentSet ( ) );
		profileDTO.set_active ( user.isActive ( ) );
		profileDTO.setRegistered_at ( user.getRegistered_at ( ) );
		
		return profileDTO;
	}
	
	public static User convertUserToEntity ( ProfileDTO userDTO ) {
		
		User user = modelMapper.map ( userDTO, User.class );
		
		user.setId ( userDTO.getId ( ) );
		user.setFirst_name ( userDTO.getFirst_name ( ) );
		user.setLast_name ( user.getLast_name ( ) );
		user.setEmail ( userDTO.getEmail ( ) );
		user.setPassword_hash ( userDTO.getPassword_hash ( ) );
		user.setProfile_image ( userDTO.getProfile_image ( ) );
		user.setGender ( userDTO.getGender ( ) );
		user.setPostSet ( userDTO.getPostSet ( ) );
		user.setCommentSet ( userDTO.getCommentSet ( ) );
		user.setActive ( userDTO.is_active ( ) );
		user.setRegistered_at ( userDTO.getRegistered_at ( ) );
		
		return user;
	}
	
	public static PostDTO convertPostToDto ( Post post ) {
		
		PostDTO postDTO = modelMapper.map ( post, PostDTO.class );
		
		postDTO.setId ( post.getId ( ) );
		postDTO.setAuthor ( post.getAuthor ( ) );
		postDTO.setMeta ( post.getMeta ( ) );
		postDTO.setSummary ( post.getSummary ( ) );
		postDTO.setContent ( post.getContent ( ) );
		postDTO.setCommentSet ( post.getCommentSet ( ) );
		postDTO.setTagSet ( post.getTagSet ( ) );
		postDTO.setCreatedAt ( post.getCreatedAt ( ) );
		postDTO.setUpdatedAt ( post.getUpdatedAt ( ) );
		postDTO.setActive ( post.isActive ( ) );
		
		return postDTO;
	}
	
	public static Post convertPostToEntity ( PostDTO postDTO ) {
		
		Post post = modelMapper.map ( postDTO, Post.class );
		
		post.setId ( postDTO.getId ( ) );
		post.setAuthor ( postDTO.getAuthor ( ) );
		post.setMeta ( postDTO.getMeta ( ) );
		post.setSummary ( postDTO.getSummary ( ) );
		post.setContent ( postDTO.getContent ( ) );
		post.setCommentSet ( postDTO.getCommentSet ( ) );
		post.setTagSet ( postDTO.getTagSet ( ) );
		post.setCreatedAt ( post.getCreatedAt ( ) );
		post.setUpdatedAt ( postDTO.getUpdatedAt ( ) );
		post.setActive ( postDTO.isActive ( ) );
		
		return post;
	}
	
	public static CommentDTO convertCommentToDto ( Comment comment ) {
		
		CommentDTO commentDTO = modelMapper.map ( comment, CommentDTO.class );
		
		commentDTO.setId ( comment.getId ( ) );
		commentDTO.setAuthor ( comment.getAuthor ( ) );
		commentDTO.setPost ( comment.getPost ( ) );
		commentDTO.setContent ( comment.getContent ( ) );
		commentDTO.setCreatedAt ( comment.getCreatedAt ( ) );
		commentDTO.setUpdatedAt ( comment.getUpdatedAt ( ) );
		commentDTO.setActive ( comment.isActive ( ) );
		
		return commentDTO;
	}
	
	public static Comment convertCommentToEntity ( CommentDTO commentDTO ) {
		
		Comment comment = modelMapper.map ( commentDTO, Comment.class );
		
		comment.setId ( commentDTO.getId ( ) );
		comment.setAuthor ( commentDTO.getAuthor ( ) );
		comment.setPost ( commentDTO.getPost ( ) );
		comment.setContent ( commentDTO.getContent ( ) );
		comment.setCreatedAt ( commentDTO.getCreatedAt ( ) );
		comment.setUpdatedAt ( commentDTO.getUpdatedAt ( ) );
		comment.setActive ( commentDTO.isActive ( ) );
		
		return comment;
	}
	
}
