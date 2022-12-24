/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Category;
import com.expastudios.blogweb.entity.Forms.NewCategoryForm;
import com.expastudios.blogweb.entity.Forms.NewPostForm;
import com.expastudios.blogweb.entity.Forms.NewTagForm;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.entity.Tag;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.repository.CategoryRepository;
import com.expastudios.blogweb.repository.PostRepository;
import com.expastudios.blogweb.repository.TagRepository;
import com.expastudios.blogweb.repository.UserRepository;
import com.expastudios.blogweb.services.IServices.PostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {

    @Autowired
    private final CategoryRepository categoryRepository;

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final TagRepository tagRepository;

    @Override
    public Optional<Post> getPost(UUID postId) {

        return postRepository.findById(postId);
    }

    @Override
    public Set<Post> getPostByUser(UUID userId, int pageNumber) {

        return postRepository.findByAuthor_IdAndAuthor_IsActiveTrue(userId);

    }

    @Override
    public ResponseEntity<?> createPost(NewPostForm postForm, HttpServletRequest request, HttpServletResponse response) {

        String email = "akgunmuhammed.95@protonmail.com"; //TokenProvider.getUsernameWithToken(request.getHeader("Authorization"));

        try {
            Optional<User> user = userRepository.findByEmailAndIsActiveTrue(email);
            Optional<Category> category = categoryRepository.findByName(postForm.getCategoryName());

            Post post = new Post();

            post.setAuthor(user.orElseThrow(() -> {
                throw new RuntimeException("Author not found!");
            }));

            post.setCategory(category.orElse(categoryRepository.findByName("Genel").orElseThrow(() -> {
                throw new RuntimeException("Category not found!");
            })));

            post.setTitle(postForm.getTitle());
            post.setMetaTitle(postForm.getMetaTitle());
            post.setContent(postForm.getContent());
            post.setCreatedAt(Zone.getCurrentTime());

            postRepository.save(post);
            return ResponseEntity.ok(true);
        } catch (Exception exc) {
            return ResponseEntity.ok(exc.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editPost(Post post, HttpServletRequest request, HttpServletResponse response) {

        try {
            postRepository
                    .findById(post.getId())
                    .map((map) -> {
                        map.setUpdatedAt(Zone.getCurrentTime());
                        return map;
                    })
                    .ifPresent((act) -> {
                        postRepository.save(post);
                    });

            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> removePost(UUID Id, HttpServletRequest request, HttpServletResponse response) {
        try {
            postRepository.deleteById(Id);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> createTag(NewTagForm newTagForm, HttpServletRequest request, HttpServletResponse response) {
        Tag tag = new Tag();
        tag.setName(newTagForm.getTagName());
        try {
            tagRepository.save(tag);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getLocalizedMessage());
        }

    }

    @Override
    public ResponseEntity<?> deleteTag(short tagId, HttpServletRequest request, HttpServletResponse response) {
        try {
            tagRepository.deleteById(tagId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> createCategory(NewCategoryForm newCategoryForm, HttpServletRequest request, HttpServletResponse response) {
        Category category = new Category();
        category.setName(newCategoryForm.getCategoryName());
        try {
            categoryRepository.save(category);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> deleteCategory(short categoryId, HttpServletRequest request, HttpServletResponse response) {
        try {
            categoryRepository.removeById(categoryId);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getLocalizedMessage());
        }
    }

}