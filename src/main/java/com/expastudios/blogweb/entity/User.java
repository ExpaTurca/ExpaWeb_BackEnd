/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import lombok.*;
import org.hibernate.annotations.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDateTime;
import java.util.*;



@Entity
@Table(name = "user")
@Getter @Setter @RequiredArgsConstructor
public class User {
	
	@Id
	@GeneratedValue ( generator = "UUID" )
	@GenericGenerator ( name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
	  @org.hibernate.annotations.Parameter ( name = "uuid_gen_strategy_class",
	                                         value = "org.hibernate.id.uuid.CustomVersionOneStrategy" ) } )
	@Column ( name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)" )
	@Type ( type = "uuid-char" )
	private UUID id;
	
	@Size ( max = 1024 ) private String bio;
	
	@Size ( min = 3, max = 32 )
	@Column ( length = 32 )
	private String firstName;
	
	@Column ( length = 32 )
	@Size ( min = 3, max = 32 )
	private String lastName;
	
	@NotNull ( message = "Bos Deger girilemez." )
	@Email ( message = "Geçersiz E-posta adresi" )
	@Size ( min = 6, max = 128 )
	@Column ( length = 128, unique = true, nullable = false )
	private String email;
	
	@NotNull ( message = "Şifre kısmı boş geçilemez." )
	@Size ( max = 60 )
	@Column ( length = 60, nullable = false )
	private String password;
	
	@Size ( max = 256 )
	@Column ( length = 256, columnDefinition = "varchar(256) default '~/profile/author.png'" )
	private String profileImage;
	
	@NotNull private char gender;
	
	@NotNull
	@Column ( columnDefinition = "boolean default 1" )
	private boolean isActive;
	
	@NotNull
	@Column ( columnDefinition = "TimeStamp default CURRENT_TIMESTAMP" )
	@UpdateTimestamp ()
	private LocalDateTime registeredAt;
	
	@UpdateTimestamp () private LocalDateTime lastLogin;
	
	@JsonIdentityInfo ( generator = ObjectIdGenerators.StringIdGenerator.class)
	@ManyToMany ( fetch = FetchType.EAGER,cascade = {CascadeType.MERGE})
	@JoinTable ( name = "User_Roles", joinColumns = { @JoinColumn ( name = "user_id" ) }, inverseJoinColumns = { @JoinColumn ( name = "role_id" ) } )
	private Set < Role > roleSet = new HashSet <> ( );
	
	@OneToMany ( orphanRemoval = true, cascade = { CascadeType.ALL } ) private Set < Post > postSet =
	  new HashSet <> ( );
	
	@OneToMany ( orphanRemoval = true, cascade = { CascadeType.ALL } ) private Set < Comment > commentSet = new HashSet <> ( );
	
}
