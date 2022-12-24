/***************************************************************
 * Copyright (c) 2022
 **************************************************************/



package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.repository.CommentRepository;
import com.expastudios.blogweb.repository.PostRepository;
import com.expastudios.blogweb.repository.UserRepository;
import com.expastudios.blogweb.services.IServices.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {

    @Autowired
    private final UserRepository userRepository;

    @Autowired
    private final PostRepository postRepository;

    @Autowired
    private final CommentRepository commentRepository;

    @Override
    public Optional<Comment> getComment(UUID commentId) {

        return commentRepository.findById(commentId);
    }

    @Override
    public Set<Comment> getCommentByPost(UUID postId, int pageNumber) {

        return commentRepository.findByPost_Id(postId);
    }

    @Override
    public ResponseEntity<Boolean> createComment(UUID postId, Comment commentEntity, HttpServletRequest request, HttpServletResponse response) {

        try {
            String email = "akgunmuhammed.95@protonmail.com";//TokenProvider.getUsernameWithToken(request.getHeader("Authorization"));
            Optional<User> user = userRepository.findByEmailAndIsActiveTrue(email);
            Optional<Post> post = postRepository.findById(postId);

            commentEntity.setCreatedAt(Zone.getCurrentTime());
            commentEntity.setAuthor(user.orElseThrow());
            commentEntity.setPost(post.orElseThrow());

            commentRepository.save(commentEntity);

            return ResponseEntity.ok(true);
        } catch (Exception exc) {
            throw new RuntimeException(exc.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> editComment(UUID commentId, Comment commentEntity, HttpServletRequest request, HttpServletResponse response) {

        commentEntity.setUpdatedAt(Zone.getCurrentTime());
        try {
            commentRepository.findById(commentId).ifPresent(commentRepository::save);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getLocalizedMessage());
        }
    }

    @Override
    public ResponseEntity<?> removeComment(UUID commentId, HttpServletRequest request, HttpServletResponse response) {

        try {
            commentRepository.findById(commentId).ifPresent(commentRepository::save);
            return ResponseEntity.ok(true);
        } catch (Exception e) {
            return ResponseEntity.ok(e.getLocalizedMessage());
        }
    }
}