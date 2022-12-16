/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.UUID;



@Entity
@Table(name = "comment")
@Getter@Setter@RequiredArgsConstructor
public class Comment {
	
	@Id
	@GeneratedValue ( generator = "UUID" )
	@GenericGenerator ( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
	  @org.hibernate.annotations.Parameter ( name = "uuid_gen_strategy_class",
	                                         value = "org.hibernate.id.uuid.CustomVersionOneStrategy" ) } )
	@Column ( name = "id", updatable = true, nullable = false, columnDefinition = "VARCHAR(36)" )
	@Type ( type = "uuid-char" )
	private UUID id;
	
	@NotNull
	@Size ( max = 512 )
	private String content;
	
	@NotNull
	@Column ( columnDefinition = "boolean default 1" )
	private boolean isActive;
	
	@NotNull
	@Column ( columnDefinition = "TimeStamp default CURRENT_TIMESTAMP" )
	@UpdateTimestamp ()
	private LocalDateTime createdAt;
	
	@UpdateTimestamp ()
	private LocalDateTime lastUpdatedAt;
	
}
