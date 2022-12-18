/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.entity.DTOs;

import com.expastudios.blogweb.entity.Role;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@RequiredArgsConstructor
public class UserDTO {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private String password;

    private LocalDateTime registeredAt;

    private LocalDateTime lastLogin;

    private Set<Role> roleSet;

    private Set<PostDTO> postSet;

    private Set<CommentDTO> commentSet;
}
