package com.expastudios.blogweb.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.*;



@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Post {
	
	@Id
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	  name = "UUID",
	  strategy = "org.hibernate.id.UUIDGenerator",
	  parameters = {
		@org.hibernate.annotations.Parameter(
		  name = "uuid_gen_strategy_class",
		  value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
		)
	  }
	)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	
	@NotNull
	@Size(max = 128)
	@Column(length = 128)
	private String title;
	
	@NotNull
	@Size(max = 64)
	@Column(length = 64)
	private String metaTitle;
	
	@NotNull
	@Size(max = 128)
	@Column(length = 128)
	private String summary;
	
	private boolean published;
	
	@NotNull
	private LocalDateTime createdAt;
	
	private LocalDateTime updatedAt;
	
	@NotNull
	@Size(max = 1024)
	@Column(length = 1024)
	private String content;
	
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "post_comment", referencedColumnName = "id")
	private Set<Comment> comments = new HashSet<>();
	
	@ManyToMany(targetEntity = Tag.class)
	@JoinTable(name = "post_tag", joinColumns = {@JoinColumn(name = "postId")}, inverseJoinColumns = {@JoinColumn(name = "tagId")})
	private Collection<Tag> tags = new HashSet<>();
	
}
