/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity.DTOs;

import lombok.*;

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

    private LocalDateTime publishedAt;

    private UserDTO author;

    private PostDTO post;

    private CommentDTO parent;

    private boolean deleteFlag;

    private boolean published;

}
