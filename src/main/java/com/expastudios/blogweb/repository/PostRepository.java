/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/



package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.Post;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


public interface PostRepository extends CrudRepository<Post, UUID> {
    List<Post> findByIsPublishedTrue();

    Optional<Post> findByIdAndIsPublishedTrue(UUID id);

    List<Post> findByAuthor_IdAndAuthor_IsActiveTrueAndIsPublishedTrue(UUID id);
}
