package com.expastudios.blogweb.model;

import com.expastudios.blogweb.entity.Tag;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Collection;
import java.util.HashSet;

@Getter
@Setter
@RequiredArgsConstructor
public class PostDTO {
	private String title;
	private String metaTitle;
	private String summary;
	private String content;
	private Collection<Tag> tags = new HashSet<>();
}
