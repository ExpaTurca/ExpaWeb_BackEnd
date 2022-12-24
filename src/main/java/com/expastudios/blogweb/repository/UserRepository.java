/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;



public interface UserRepository extends JpaRepository<User, UUID> {

    Optional<User> findByIdAndIsActiveTrue(UUID Id);

    Optional<User> findByEmailAndIsActiveTrue(String email);

    Optional<User> findByUsernameAndIsActiveTrue(String username);

}
