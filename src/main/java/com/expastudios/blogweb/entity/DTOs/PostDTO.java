/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity.DTOs;

import com.expastudios.blogweb.entity.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;


@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {

    private UUID id;

    //Shown at Post Page and the lists.
    private String title;

    //SEO and browser title
    private String metaTitle;

    //Post Data
    private String content;

    private LocalDateTime createdAt;

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    private Set<Category> categorySet = new java.util.LinkedHashSet<>();

    private Set<Tag> tagSet = new java.util.LinkedHashSet<>();

    //Author
    private User author;

    //Parent
    private Post parent;

    //Comments
    private Set<Comment> commentSet = new java.util.LinkedHashSet<>();

    private boolean published;
}