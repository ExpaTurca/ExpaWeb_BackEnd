package com.expastudios.blogweb.entity;

import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

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
	@GeneratedValue(generator = "UUID")
	@GenericGenerator(
	  name = "UUID",
	  strategy = "org.hibernate.id.UUIDGenerator",
	  parameters = {
		@org.hibernate.annotations.Parameter(
		  name = "uuid_gen_strategy_class",
		  value = "org.hibernate.id.uuid.CustomVersionOneStrategy"
		)
	  }
	)
	@Column(columnDefinition = "BINARY(16)")
	private UUID id;
	
	@Size(min = 3, max = 32)
	@Column(length = 32)
	private String first_name;
	
	@Column(length = 32)
	@Size(min = 3, max = 32)
	private String last_name;
	
	@Size(max = 256)
	@Column(length = 256, columnDefinition = "varchar(256) default '~/profile/user.png'")
	private String profile_image;
	
	@NotNull
	private char gender;
	
	@Email
	@Size(min = 6, max = 128)
	@NotNull(message = "Bos Deger girilemez.")
	@Column(length = 128, unique = true, nullable = false)
	private String email;
	
	@NotNull(message = "Bos Deger girilemez.")
	@Size(max = 60)
	@Column(length = 60, nullable = false)
	private String password_hash;
	
	@NotNull
	@Column(columnDefinition = "TimeStamp default CURRENT_TIMESTAMP")
	@UpdateTimestamp()
	private LocalDateTime registered_at;
	
	@UpdateTimestamp()
	private LocalDateTime last_login;
	
	@Size(max = 1024)
	private String bio;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinTable(
	  joinColumns = {@JoinColumn(name = "user_id")},
	  inverseJoinColumns = {@JoinColumn(name = "role_id")}
	)
	private Set<Role> roles;
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_post", referencedColumnName = "id")
	private Set<Post> posts = new HashSet<>();
	
	@OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	@JoinColumn(name = "profile_comment", referencedColumnName = "id")
	private Set<Comment> comments = new HashSet<>();
	
}
