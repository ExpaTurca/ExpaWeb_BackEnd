/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/



package com.expastudios.blogweb.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;


@Entity(name = "e_post")
@Table(name = "t_post", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class Post implements java.io.Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
					value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
	@Column(nullable = false, insertable = false, updatable = false)
	@Type(type = "uuid-char")
	private UUID id;

	@NotNull
	@Size(max = 32)
	private String title;

	@NotNull
	@Size(max = 128)
	private String metaTitle;

	@NotNull
	@Size(max = 1024)
	private String content;

	@NotNull
	@Column(columnDefinition = "TimeStamp default CURRENT_TIMESTAMP")
	@CreationTimestamp()
	private LocalDateTime createdAt;

	@UpdateTimestamp()
	private LocalDateTime editedAt;

	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Account author;

	@OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
	private Set<Comment> commentSet = new HashSet<>();

	@NotNull
	@Column(columnDefinition = "boolean default true")
	private boolean isPublished;
}