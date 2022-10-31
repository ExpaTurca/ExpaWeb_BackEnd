/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;



@Entity
@Setter
@RequiredArgsConstructor
public class Post {
	
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
	
	@NotNull
	@Size ( max = 64 )
	@Column ( length = 64 )
	private String meta;
	
	@NotNull
	@Size ( max = 128 )
	@Column ( length = 128 )
	private String summary;
	
	@NotNull
	@Size ( max = 1024 )
	@Column ( length = 1024 )
	private String content;
	
	@NotNull private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@OneToMany ( fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "post" ) private Set < Comment >
	  commentSet = new HashSet <> ( );
	
	@ManyToMany ( targetEntity = Tag.class )
	@JoinTable ( name = "post_tag", joinColumns = { @JoinColumn ( name = "post_Id" ) },
	             inverseJoinColumns = { @JoinColumn ( name = "tag_Id" ) } )
	private Set < Tag > tagSet = new HashSet <> ( );
	
	@NotNull
	@Column ( columnDefinition = "boolean default 1" )
	private boolean isActive;
	
	@JsonBackReference
	public User getAuthor ( ) {
		
		return author;
	}
	
	@JsonManagedReference
	public Set < Comment > getCommentSet ( ) {
		
		return commentSet;
	}
	
	public UUID getId ( ) {
		
		return id;
	}
	
	public String getMeta ( ) {
		
		return meta;
	}
	
	public String getSummary ( ) {
		
		return summary;
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
	
	public Set < Tag > getTagSet ( ) {
		
		return tagSet;
	}
	
	public boolean isActive ( ) {
		
		return isActive;
	}
	
}
