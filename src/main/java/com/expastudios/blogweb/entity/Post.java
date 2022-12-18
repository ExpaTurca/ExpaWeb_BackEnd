/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

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
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
					value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	//@Type ( type = "uuid-char" )
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
	private LocalDateTime publishedAt;

	@UpdateTimestamp()
	private LocalDateTime updatedAt;

	@ManyToMany(cascade = {CascadeType.MERGE})
	@JoinTable(name = "PostCategory", joinColumns = {@JoinColumn(name = "postId")}, inverseJoinColumns =
			{@JoinColumn(name = "categoryId")})
	private Set<Category> categorySet;

	@ManyToMany(cascade = {CascadeType.MERGE})
	@JoinTable(name = "PostTag", joinColumns = {@JoinColumn(name = "postId")}, inverseJoinColumns =
			{@JoinColumn(name = "tagId")})
	private Set<Tag> tagSet = new HashSet<>();

	@NotNull
	@ManyToOne
	@JoinColumn(name = "authorId")
	private User author;

	@ManyToOne
	@JoinColumn(name = "parentId")
	private Post parent;

	@OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL})
	private Set<Comment> commentSet = new HashSet<>();

	@NotNull
	@Column(columnDefinition = "boolean default 0")
	private boolean deleteFlag;

	@NotNull
	@Column(columnDefinition = "boolean default 0")
	private boolean isPublished;
}