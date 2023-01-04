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
import java.util.UUID;


@Entity(name = Comment.ENTITY_NAME)
@Table(name = Comment.TABLE_NAME, schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class Comment implements java.io.Serializable {

	public static final String ENTITY_NAME = "e_comment";
	public static final String TABLE_NAME = "t_comment";
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@GenericGenerator(name = "uuid-char", strategy = "org.hibernate.id.uuid.CustomVersionOneStrategy", parameters = {
			@org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
					value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
	@Column(nullable = false, insertable = false, updatable = false)
	@Type(type = "uuid-char")
	private UUID id;

	@NotNull
	@Size(max = 512)
	private String content;

	@NotNull
	@Column(columnDefinition = "TimeStamp default CURRENT_TIMESTAMP")
	@CreationTimestamp()
	private LocalDateTime createdAt;

	@UpdateTimestamp()
	private LocalDateTime editedAt;

	@NotNull(message = "Yorumu kim yazdı?")
	@ManyToOne
	@JoinColumn(name = "user_id", nullable = false)
	private Account author;

	@NotNull(message = "Hangi paylaşıma yorum yapıldığını bilmem gerekiyor.")
	@ManyToOne
	@JoinColumn(name = "post_id", nullable = false)
	private Post post;


	@NotNull
	@Column(columnDefinition = "boolean default true")
	private boolean isPublished;

}