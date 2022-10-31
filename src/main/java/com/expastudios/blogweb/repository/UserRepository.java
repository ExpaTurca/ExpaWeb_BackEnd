/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;



@Repository
public interface UserRepository extends JpaRepository < User, UUID > {
	
	Optional < User > findByEmail ( String email );
	
}
