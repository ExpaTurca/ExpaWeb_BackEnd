/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;



@Repository
public interface CommentRepository extends JpaRepository<Comment, UUID> {

	Optional<Comment> findByIdAndDeleteFlagFalse(UUID id);
}