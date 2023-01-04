/***************************************************************
 * Copyright (c) 2022-2023
 **************************************************************/
package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.entity.DTOs.PostDTO;
import com.expastudios.blogweb.entity.Forms.NewPostForm;
import com.expastudios.blogweb.entity.Forms.RequestFromUUID;
import com.expastudios.blogweb.services.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.websocket.server.PathParam;
import java.util.Optional;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/post", consumes = {"application/xml", "application/json"})
@RequiredArgsConstructor
public class PostEndpoint {

	private final PostService postService;

	@GetMapping(value = "/{id}")
	public Optional<PostDTO> getById(@PathVariable("id") UUID id) {

		return Optional.ofNullable(postService.getById(id));
	}

	@GetMapping(value = "/by/user")
	public Page<PostDTO> getAllByUserId(@RequestBody RequestFromUUID requestFromUUID, @PathParam(value = "pg") int pg, @PathParam(value = "sz") int sz) {
		return postService.getByUserId(requestFromUUID.getUuid(), pg, sz);
	}

	@PostMapping(value = "/create", consumes = {"application/xml", "application/json"})
	public String create(@RequestBody NewPostForm newPostForm,
						 HttpServletRequest request,
						 HttpServletResponse response)
			throws ClassNotFoundException {
		return postService.create(newPostForm, "akgunmuhammed.95@protonmail.com");
	}

	@PostMapping(value = "/edit")
	public String edit(@RequestBody PostDTO postDto,
					   HttpServletRequest request,
					   HttpServletResponse response) {
		return postService.edit(postDto, request, response);
	}

	@PostMapping("/delete/{id}")
	public String remove(@PathVariable("id") UUID id,
						 HttpServletRequest request,
						 HttpServletResponse response) {

		return postService.remove(id, request, response);
	}

}