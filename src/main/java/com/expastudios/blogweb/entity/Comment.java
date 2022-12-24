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
import java.util.UUID;


@Entity(name = "e_comment")
@Table(name = "t_comment", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class Comment {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
					value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
	@Column(nullable = false, insertable = false, updatable = false, columnDefinition = "VARCHAR(36)")
	private UUID id;

	@NotNull
	@Size(max = 512)
	private String content;

	@NotNull
	@Column(columnDefinition = "TimeStamp default CURRENT_TIMESTAMP")
	@UpdateTimestamp()
	private LocalDateTime createdAt;

	@UpdateTimestamp()
	private LocalDateTime updatedAt;

	@NotNull(message = "Yorumu kimi yaptığını bilmem gerekiyor.")
	@ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
	@JoinColumn(name = "authorId")
	@Fetch(FetchMode.SELECT)
	private User author;

	@NotNull(message = "Hangi paylaşıma yorum yapıldığını bilmem gerekiyor.")
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "postId")
	@Fetch(FetchMode.SELECT)
	private Post post;

	@NotNull
	@Column(columnDefinition = "boolean default false")
	private boolean published;

}