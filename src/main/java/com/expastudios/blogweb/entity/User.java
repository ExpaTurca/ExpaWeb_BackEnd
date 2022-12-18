/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity;

import com.expastudios.blogweb.annotations.Username;
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
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
			@org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
					value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
	@Column(name = "id", updatable = false, nullable = false, columnDefinition = "VARCHAR(36)")
	@Type(type = "uuid-char")
	private UUID id;

	@Size(min = 3, max = 32)
	@Column(length = 32)
	private String firstName;

	@Column(length = 32)
	@Size(min = 3, max = 32)
	private String lastName;

	@NotNull
	@Email
	@Size(min = 6, max = 128)
	@Column(length = 128, unique = true, nullable = false)
	private String email;

	@NotNull
	@Username
	@Size(min = 3, max = 8)
	@Column(length = 128, unique = true, nullable = false)
	private String username;

	@NotNull
	@Size(max = 60)
	@Column(length = 60, nullable = false)
	private String password;

	@NotNull
	@Column(columnDefinition = "TimeStamp default CURRENT_TIMESTAMP")
	@UpdateTimestamp()
	private LocalDateTime registeredAt;

	@UpdateTimestamp()
	private LocalDateTime lastLogin;

	@ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
	@JoinTable(name = "User_Role", joinColumns = {@JoinColumn(name = "userId")}, inverseJoinColumns = {
			@JoinColumn(name = "roleId")})
	private Set<Role> roleSet = new HashSet<>();

	//@JsonIdentityInfo ( generator = ObjectIdGenerators.UUIDGenerator.class )
	@OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL})
	private Set<Post> postSet = new HashSet<>();

	@OneToMany(orphanRemoval = true, cascade = {CascadeType.ALL})
	private Set<Comment> commentSet =
			new HashSet<>();

	@NotNull
	@Column(columnDefinition = "boolean default 0")
	private boolean deleteFlag;

}
