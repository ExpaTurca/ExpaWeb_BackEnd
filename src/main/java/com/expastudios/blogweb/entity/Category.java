/***************************************************************
 * Copyright (c) 2022
 **************************************************************/


package com.expastudios.blogweb.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity(name = "e_category")
@Table(name = "t_category", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@SequenceGenerator(name = "sequence", sequenceName = "SEQUENCE")
	private short id;

	@NotNull
	@Size(max = 32)
	private String name;

	@OneToMany(cascade = {CascadeType.ALL}, mappedBy = "category")
	private Set<Post> post = new java.util.LinkedHashSet<>();
}