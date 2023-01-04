/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/
package com.expastudios.blogweb.entity.DTOs;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;


@Getter
@Setter
@RequiredArgsConstructor
public class AccountDTO {

    private UUID id;

    private String firstName;

    private String lastName;

    private String email;

    private String username;

    private LocalDateTime registeredAt;
}