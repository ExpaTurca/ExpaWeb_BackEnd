/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services.IServices;

import com.expastudios.blogweb.entity.Forms.NewCategoryForm;
import com.expastudios.blogweb.entity.Forms.NewPostForm;
import com.expastudios.blogweb.entity.Forms.NewTagForm;
import com.expastudios.blogweb.entity.Post;
import org.springframework.http.ResponseEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


public interface PostService {

    Optional<Post> getPost(UUID postId);

    Set<Post> getPostByUser(UUID userId, int pageNumber) throws ClassNotFoundException;

    ResponseEntity<?> createPost(NewPostForm newPostForm, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> editPost(Post post, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> removePost(UUID Id, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> createTag(NewTagForm newTagForm, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException;

    ResponseEntity<?> deleteTag(short tagId, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> createCategory(NewCategoryForm newCategoryForm, HttpServletRequest request, HttpServletResponse response);

    ResponseEntity<?> deleteCategory(short categoryId, HttpServletRequest request, HttpServletResponse response);

}