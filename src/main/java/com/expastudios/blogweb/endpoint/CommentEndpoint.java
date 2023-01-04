/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/



package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.entity.DTOs.CommentDTO;
import com.expastudios.blogweb.entity.Forms.NewCommentForm;
import com.expastudios.blogweb.entity.Forms.RequestFromUUID;
import com.expastudios.blogweb.services.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/comment")
@RequiredArgsConstructor
public class CommentEndpoint {

    private final CommentService commentService;

    @GetMapping(value = "/by/user")
    public Page<CommentDTO> getAllByPostId(@RequestBody RequestFromUUID requestFromUUID, @PathParam(value = "pg") int pg, @PathParam(value = "sz") int sz) {
        return commentService.getByPostId(requestFromUUID.getUuid(), pg, sz);
    }

    @PostMapping(value = "/create", consumes = {"application/json"})
    public String create(@RequestBody NewCommentForm commentForm, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {

        return commentService.create(commentForm, "akgunmuhammed.95@protonmail.com");
    }

    @PostMapping(value = "/edit")
    public String edit(@RequestBody CommentDTO commentDTO, HttpServletRequest request, HttpServletResponse response) {
        return commentService.edit(commentDTO, request, response);
    }

    @PostMapping(value = "/{id}/remove")
    public String remove(@PathVariable(value = "id") UUID id, HttpServletRequest request, HttpServletResponse response) {

        return commentService.remove(id, request, response);
    }
}