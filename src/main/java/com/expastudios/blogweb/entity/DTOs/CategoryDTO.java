/***************************************************************
 * Copyright (c) 2022
 **************************************************************/


package com.expastudios.blogweb.entity.DTOs;

import com.expastudios.blogweb.entity.Post;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Set;


@Getter
@Setter
@RequiredArgsConstructor
public class CategoryDTO {

    private int id;

    private String name;

    private Set<Post> postSet = new java.util.LinkedHashSet<>();

}