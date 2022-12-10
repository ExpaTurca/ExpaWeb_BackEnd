/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class Role {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.SEQUENCE, generator = "sequence" )
	@SequenceGenerator ( name = "sequence", sequenceName = "SEQUENCE" )
	private short id;
	
	private String name;
	
	@ManyToMany ( fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roles" ) private Set < User > users;
	
}
