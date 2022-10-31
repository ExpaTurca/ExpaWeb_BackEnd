/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@Entity
@Setter
@RequiredArgsConstructor
public class Role {
	
	@Id
	@GeneratedValue ( strategy = GenerationType.SEQUENCE, generator = "sequence" )
	@SequenceGenerator ( name = "sequence", sequenceName = "SEQUENCE" )
	private short id;
	
	private String roleName;
	
	@ManyToMany ( fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "roles" ) private Set < User > users;
	
	public short getId ( ) {
		
		return id;
	}
	
	public String getRoleName ( ) {
		
		return roleName;
	}
	
	@JsonManagedReference
	public Set < User > getUsers ( ) {
		
		return users;
	}
	
}
