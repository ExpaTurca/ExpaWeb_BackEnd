/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;



public interface PostRepository extends CrudRepository< Post, UUID > {
	
	Optional<Post> findByIdAndDeleteFlagFalse(UUID id);
	}
