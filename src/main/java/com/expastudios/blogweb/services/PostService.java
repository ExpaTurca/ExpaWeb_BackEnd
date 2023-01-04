/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/
package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Account;
import com.expastudios.blogweb.entity.DTOs.PostDTO;
import com.expastudios.blogweb.entity.Forms.NewPostForm;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.repository.PostRepository;
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
public class PostService {

    private final ModelMapper mapper;

    private final AccountService accountService;

    private final PostRepository postRepository;

    public List<PostDTO> getAll() {
        return postRepository.findByIsPublishedTrue().stream().map(map -> mapper.map(map, PostDTO.class)).toList();
    }

    public PostDTO getById(UUID postId) {
        return mapper.map(postRepository.findByIdAndIsPublishedTrue(postId).orElseThrow(() -> {
            throw new RuntimeException("Post not found");
        }), PostDTO.class);
    }

    public Page<PostDTO> getByUserId(UUID userId, int pageNumber, int size) {
        List<PostDTO> list = postRepository.findByAuthor_IdAndAuthor_IsActiveTrueAndIsPublishedTrue(userId).
                stream().map(map -> mapper.map(map, PostDTO.class)).toList();
        Pageable page = PageRequest.of(pageNumber, size);
        int total = list.size();
        return new PageImpl<>(list, page, total);
    }

    public String create(NewPostForm postForm, String mail) throws ClassNotFoundException {

        Account user = accountService.getByEmail(mail);

        Post post = new Post();
        post.setAuthor(user);
        post.setTitle(postForm.getTitle());
        post.setMetaTitle(postForm.getMetaTitle());
        post.setContent(postForm.getContent());
        post.setCreatedAt(Zone.getCurrentTime());
        post.setPublished(true);

        try {
            postRepository.save(post);
            return "success-message";
        } catch (Exception exc) {
            return exc.getLocalizedMessage();
        }
    }

    public String edit(PostDTO postDto, HttpServletRequest request, HttpServletResponse response) {

        try {
            postRepository.findById(postDto.getId())
                    .map((map) -> {
                        map = mapper.map(postDto, Post.class);
                        map.setEditedAt(Zone.getCurrentTime());
                        return postRepository.save(map);
                    }).orElseThrow(() -> {
                        throw new RuntimeException("Uyarı! Değişiklik yapılmadı!");
                    });

            return "success-message";
        } catch (Exception exc) {
            return exc.getLocalizedMessage();
        }
    }

    public String remove(UUID Id, HttpServletRequest request, HttpServletResponse response) {
        try {
            postRepository.findByIdAndIsPublishedTrue(Id).map(map -> {
                map.setPublished(false);
                return postRepository.save(map);
            }).orElseThrow(() -> {
                throw new RuntimeException("Hata! Paylaşım kaldırılmadı!");
            });
            return "success-message";
        } catch (Exception exc) {
            return exc.getLocalizedMessage();
        }
    }
}