/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.repository;


import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;



@Repository
public interface PostRepository extends JpaRepository < Post, UUID > {
	
	Page < Comment > findAllCommentsById ( UUID Id, Pageable pageable );
	
}
