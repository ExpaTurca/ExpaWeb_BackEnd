/***************************************************************
 * Copyright (c) 2022
 **************************************************************/


package com.expastudios.blogweb.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Table(name = "category")
@Getter
@Setter
@RequiredArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@SequenceGenerator(name = "sequence", sequenceName = "SEQUENCE")
	private int id;

	@NotNull
	@Size(max = 64)
	private String title;

	@Size(max = 96)
	private String metaTitle;

	@NotNull
	@Size(max = 512)
	private String content;

	@ManyToMany(cascade = {CascadeType.MERGE}, mappedBy = "categorySet")
	private Set<Post> post;

}
