/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.repository;

import com.expastudios.blogweb.entity.Tag;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TagRepository extends JpaRepository<Tag, Short> {

}
