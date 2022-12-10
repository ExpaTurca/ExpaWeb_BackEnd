/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity;

import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;



@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class User {
	
	@Id
	@GeneratedValue ( generator = "UUID" )
	@GenericGenerator ( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
	  @org.hibernate.annotations.Parameter ( name = "uuid_gen_strategy_class",
	                                         value = "org.hibernate.id.uuid.CustomVersionOneStrategy" ) } )
	@Column ( name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)" )
	@Type ( type = "uuid-char")
	private UUID id;
	
	@Size ( min = 3, max = 32 )
	@Column ( length = 32 )
	private String firstName;
	
	@Column ( length = 32 )
	@Size ( min = 3, max = 32 )
	private String lastName;
	
	@Size ( max = 256 )
	@Column ( length = 256, columnDefinition = "varchar(256) default '~/profile/author.png'" )
	private String profileImage;
	
	@NotNull private char gender;
	
	@Email
	@Size ( min = 6, max = 128 )
	@NotNull ( message = "Bos Deger girilemez." )
	@Column ( length = 128, unique = true, nullable = false )
	private String email;
	
	@NotNull ( message = "Şifre kısmı boş geçilemez." )
	@Size ( max = 60 )
	@Column ( length = 60, nullable = false )
	private String password;
	
	@NotNull
	@Column ( columnDefinition = "boolean default 1" )
	private boolean isActive;
	
	@NotNull
	@Column ( columnDefinition = "TimeStamp default CURRENT_TIMESTAMP" )
	@UpdateTimestamp ()
	private LocalDateTime registeredAt;
	
	@UpdateTimestamp () private LocalDateTime lastLogin;
	
	@Size ( max = 1024 ) private String bio;
	
	@ManyToMany ( fetch = FetchType.EAGER, cascade = CascadeType.ALL )
	@JoinTable ( joinColumns = { @JoinColumn ( name = "user_id" ) },
	             inverseJoinColumns = { @JoinColumn ( name = "role_id" ) } )
	private Set < Role > roles;
	
	@OneToMany ( fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "author" ) private Set < Post >
	  postSet = new HashSet <> ( );
	
	//@OneToMany ( fetch = FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "author" )
	//private Set < Comment >
	//  commentSet = new HashSet <> ( );
	
}
