/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;


@Entity(name = "e_tag")
@Table(name = "t_tag", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class Tag {

	@Id
	@GeneratedValue
	private short id;

	@NotNull
	@Size(max = 32)
	private String name;

}