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
public class CommentDTO {

    private UUID id;

    private String content;

    private LocalDateTime createdAt;

    private AccountDTO author;

    private PostDTO post;

}