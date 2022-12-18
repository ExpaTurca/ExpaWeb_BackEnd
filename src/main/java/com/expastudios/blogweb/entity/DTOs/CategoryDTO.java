/***************************************************************
 * Copyright (c) 2022
 **************************************************************/


package com.expastudios.blogweb.entity.DTOs;

import lombok.*;

import java.util.Set;


@Getter
@Setter
@RequiredArgsConstructor
public class CategoryDTO {

    private int id;

    private String title;

    private String metaTitle;

    private String content;

    private Set<PostDTO> postSet;

}
