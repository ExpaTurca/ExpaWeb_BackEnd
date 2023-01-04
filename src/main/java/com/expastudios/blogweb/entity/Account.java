/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/
package com.expastudios.blogweb.entity;

import com.expastudios.blogweb.annotations.Username;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.UUID;


@Entity(name = "e_user")
@Table(name = "t_user", schema = "public")
@Getter
@Setter
@RequiredArgsConstructor
public class Account implements java.io.Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator", parameters = {
            @org.hibernate.annotations.Parameter(name = "uuid_gen_strategy_class",
                    value = "org.hibernate.id.uuid.CustomVersionOneStrategy")})
    @Column(nullable = false, insertable = false, updatable = false)
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
    @Size(min = 6, max = 64)
    @Column(length = 64, unique = true, nullable = false)
    private String email;

    @NotNull
    @Username
    @Size(min = 3, max = 8)
    @Column(length = 64, unique = true, nullable = false)
    private String username;

    @NotNull
    @Size(max = 60)
    @Column(length = 60, nullable = false)
    private String password;

    @NotNull
    @Column(updatable = false, columnDefinition = "TimeStamp default CURRENT_TIMESTAMP")
    @UpdateTimestamp()
    private LocalDateTime registeredAt;

    private LocalDateTime lastLogin;

    @OneToMany(mappedBy = "author", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Post> postSet = new LinkedHashSet<>();

    @OneToMany(mappedBy = "author", cascade = {CascadeType.ALL}, orphanRemoval = true)
    private Set<Comment> commentSet = new LinkedHashSet<>();

    @Column(nullable = false, columnDefinition = "boolean default true")
    private @NotNull boolean isActive;

}