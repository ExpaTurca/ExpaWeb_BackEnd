/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;



@Entity
@Table(name = "post")
@Getter @Setter @RequiredArgsConstructor
@JsonIdentityInfo ( generator = ObjectIdGenerators.UUIDGenerator.class, property = "id" )
public class Post {
	
	@Id
	@GeneratedValue ( generator = "UUID" )
	@GenericGenerator ( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
	  @org.hibernate.annotations.Parameter ( name = "uuid_gen_strategy_class",
	                                         value = "org.hibernate.id.uuid.CustomVersionOneStrategy" ) } )
	@Column ( name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)" )
	@Type ( type = "uuid-char" )
	private UUID id;
	
	@NotNull
	@Size ( max = 32 )
	private String title;
	
	@NotNull
	@Size ( max = 128 )
	private String description;
	
	@NotNull
	@Size ( max = 1024 )
	private String content;
	
	@NotNull
	@Column ( columnDefinition = "boolean default 1" )
	private boolean isActive;
	
	@NotNull
	@Column ( columnDefinition = "TimeStamp default CURRENT_TIMESTAMP" )
	@UpdateTimestamp ()
	private LocalDateTime createdAt;
	
	@NotNull
	@UpdateTimestamp ()
	private LocalDateTime lastUpdatedAt;
	
	@OneToMany (orphanRemoval = true, cascade = { CascadeType.ALL } )
	private Set < Comment > commentSet = new HashSet <> ( );
	
	@ManyToMany ( cascade = { CascadeType.PERSIST, CascadeType.MERGE } )
	@JoinTable ( name = "PostTag", joinColumns = { @JoinColumn ( name = "post_id" ) }, inverseJoinColumns = { @JoinColumn ( name = "tag_id" ) } )
	private Set < Tag > tagSet = new HashSet <> ( );
	
}
