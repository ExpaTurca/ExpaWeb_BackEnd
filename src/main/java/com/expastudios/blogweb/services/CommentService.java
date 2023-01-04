/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/


package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Account;
import com.expastudios.blogweb.entity.Comment;
import com.expastudios.blogweb.entity.DTOs.CommentDTO;
import com.expastudios.blogweb.entity.DTOs.PostDTO;
import com.expastudios.blogweb.entity.Forms.NewCommentForm;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.repository.CommentRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.UUID;


@Service
@RequiredArgsConstructor
public class CommentService {

    private final ModelMapper mapper;

    private final AccountService accountService;

    private final PostService postService;

    private final CommentRepository commentRepository;

    public Page<CommentDTO> getAll(int pg, int sz) {
        Pageable page = PageRequest.of(0, pg);
        List<CommentDTO> list = commentRepository.findByIsPublishedTrue().stream().map(map -> mapper.map(map, CommentDTO.class)).toList();
        int total = list.size();
        return new PageImpl<>(list, page, total);
    }

    public Page<CommentDTO> getByPostId(UUID postId, int pageNumber, int size) {
        Pageable page = PageRequest.of(pageNumber, size);
        List<CommentDTO> list = commentRepository.findByPost_IdAndPost_IsPublishedTrue(postId).stream().
                map(map -> mapper.map(map, CommentDTO.class)).toList();
        int total = list.size();
        return new PageImpl<>(list, page, total);
    }

    public String create(NewCommentForm commentForm, String mail) {
        Account user = accountService.getByEmail(mail);
        PostDTO postDto = postService.getById(commentForm.getPostId());

        Comment comment = new Comment();
        comment.setAuthor(user);
        comment.setPost(mapper.map(postDto, Post.class));
        comment.setCreatedAt(Zone.getCurrentTime());
        comment.setContent(commentForm.getComment());
        comment.setPublished(true);

        try {
            commentRepository.save(comment);
            return "success-message";
        } catch (Exception exc) {
            return "Uyarı! Yorum yapılamadı!";
        }
    }

    public String edit(CommentDTO commentDto, HttpServletRequest request, HttpServletResponse response) {

        try {
            commentRepository.findByIdAndIsPublishedTrue(commentDto.getId()).map(map ->
            {
                map = mapper.map(commentDto, Comment.class);
                map.setEditedAt(Zone.getCurrentTime());
                return commentRepository.save(map);
            }).orElseThrow(() -> {
                throw new RuntimeException("Uyarı! Değişiklik yapılmadı! \n");
            });
            return "success-message";
        } catch (Exception exc) {
            return exc.getLocalizedMessage();
        }
    }

    public String remove(UUID commentId, HttpServletRequest request, HttpServletResponse response) {

        try {
            commentRepository.findById(commentId).map(map -> {
                map.setPublished(false);
                return commentRepository.save(map);
            });
            return "success-message";
        } catch (Exception exc) {
            return exc.getLocalizedMessage();
        }
    }
}