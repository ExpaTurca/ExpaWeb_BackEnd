/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity;


import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Setter
@RequiredArgsConstructor
public class Comment {
	
	@Id
	@GeneratedValue ( generator = "UUID" )
	@GenericGenerator ( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
	  @org.hibernate.annotations.Parameter ( name = "uuid_gen_strategy_class",
	                                         value = "org.hibernate.id.uuid.CustomVersionOneStrategy" ) } )
	@Column ( columnDefinition = "BINARY(16)" )
	private UUID id;
	
	@ManyToOne
	@JoinColumn ( name = "author_id" )
	private User author;
	
	@ManyToOne
	@JoinColumn ( name = "post_id" )
	private Post post;
	
	@NotNull
	@Size ( max = 1024 )
	@Column ( length = 1024 )
	private String content;
	
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@NotNull
	@Column ( columnDefinition = "boolean default 1" )
	private boolean isActive;
	
	@JsonBackReference
	public User getAuthor ( ) {
		
		return author;
	}
	
	@JsonBackReference
	public Post getPost ( ) {
		
		return post;
	}
	
	public UUID getId ( ) {
		
		return id;
	}
	
	public String getContent ( ) {
		
		return content;
	}
	
	public LocalDateTime getCreatedAt ( ) {
		
		return createdAt;
	}
	
	public LocalDateTime getUpdatedAt ( ) {
		
		return updatedAt;
	}
	
	public boolean isActive ( ) {
		
		return isActive;
	}
	
}
