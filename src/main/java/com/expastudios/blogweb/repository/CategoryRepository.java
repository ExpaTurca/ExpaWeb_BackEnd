/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Short> {
    long removeById(short id);

    Optional<Category> findByName(String name);
}