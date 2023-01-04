/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/



package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;


@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {
    List<Comment> findByIsPublishedTrue();


    List<Comment> findByPost_IdAndPost_IsPublishedTrue(UUID postId);

    Optional<Comment> findByIdAndIsPublishedTrue(UUID commentId);
}