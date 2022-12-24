/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Entity(name = "e_post")
@Table(name = "t_post", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class Post {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
					value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "VARCHAR(36)")
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
	@UpdateTimestamp()
	private LocalDateTime createdAt;

	@UpdateTimestamp()
	private LocalDateTime updatedAt;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "categoryId")
	private Category category;

	@NotNull
	@ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "user_id")
	@Fetch(FetchMode.SELECT)
	private User author;

	@OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
	@Fetch(FetchMode.JOIN)
	private Set<Comment> commentSet = new java.util.LinkedHashSet<>();

	@NotNull
	@Column(columnDefinition = "boolean default true")
	private boolean isPublished;
}