/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity.DTOs;

import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.entity.User;
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

    private LocalDateTime updatedAt;

    private User author;

    private Post post;

    private Comment parent;


    private boolean published;

}
