/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "tag")
@Getter @Setter @RequiredArgsConstructor
public class Tag {
	
	@Id
	@GeneratedValue
	private short id;
	
	@Size ( max = 32 ) private String name;
	
	@ManyToMany ( cascade = { CascadeType.PERSIST, CascadeType.MERGE }, mappedBy = "tagSet" ) private Set < Post >
	  postSet = new HashSet <> ( );
	
}
