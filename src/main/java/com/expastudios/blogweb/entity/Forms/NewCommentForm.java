/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/
package com.expastudios.blogweb.entity.Forms;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.UUID;

@Getter
@Setter
@RequestMapping
public class NewCommentForm {
    private UUID postId;
    private String comment;
}
