/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue ( generator = "UUID" )
	@GenericGenerator ( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
	  @org.hibernate.annotations.Parameter ( name = "uuid_gen_strategy_class",
	                                         value = "org.hibernate.id.uuid.CustomVersionOneStrategy" ) } )
	private UUID id;
	
	@NotNull
	@Size(max = 32)
	private String title;
	
	@NotNull
	@Size(max = 128)
	private String description;
	
	@NotNull
	@Size(max = 1024)
	private String content;
	
	@NotNull
	@Column ( columnDefinition = "boolean default 1" )
	private boolean isActive;
	
	@NotNull
	@Column ( columnDefinition = "TimeStamp default CURRENT_TIMESTAMP" )
	@UpdateTimestamp ()
	private LocalDateTime createdAt;
	
	@ManyToOne
	@JoinColumn ( name = "author_id" )
	private User author;
	
}
