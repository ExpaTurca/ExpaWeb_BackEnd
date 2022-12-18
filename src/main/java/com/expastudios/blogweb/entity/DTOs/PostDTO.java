/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.entity.DTOs;

import com.expastudios.blogweb.entity.Category;
import com.expastudios.blogweb.entity.Tag;
import lombok.*;

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

    private LocalDateTime publishedAt;

    private LocalDateTime updatedAt;

    private Set<Category> categorySet;

    private Set<Tag> tagSet;

    //Author
    private UserDTO author;

    //Parent
    private PostDTO parent;

    //Comments
    private Set<CommentDTO> commentSet;

    private boolean deleteFlag;

    private boolean isPublished;
}