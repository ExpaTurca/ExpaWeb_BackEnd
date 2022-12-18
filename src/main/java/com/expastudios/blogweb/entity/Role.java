/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Set;


@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Role {

	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "sequence")
	@SequenceGenerator(name = "sequence", sequenceName = "SEQUENCE")
	private int id;

	@NotNull
	private String name;

	@JsonIdentityInfo(generator = ObjectIdGenerators.IntSequenceGenerator.class)
	@ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE})
	private Set<User> userSet;

}