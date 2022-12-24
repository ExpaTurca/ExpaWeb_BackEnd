/***************************************************************
 * Copyright (c) 2022
 **************************************************************/
package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.Util.EntityDtoConversion;
import com.expastudios.blogweb.entity.DTOs.PostDTO;
import com.expastudios.blogweb.entity.Forms.NewCategoryForm;
import com.expastudios.blogweb.entity.Forms.NewPostForm;
import com.expastudios.blogweb.entity.Forms.NewTagForm;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.services.IServices.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


@RestController
@RequestMapping(value = "/api", consumes = {"application/xml", "application/json"})
@RequiredArgsConstructor
public class PostEndpoint {

	@Autowired
	private final PostService postService;

	@GetMapping("/post")
	public Optional<PostDTO> getPost(@RequestParam String postId) throws ClassNotFoundException {

		return Optional.of((PostDTO) EntityDtoConversion.ConvertToDTO(postService.getPost(UUID.fromString(postId)).orElseThrow()));
	}

	@GetMapping("/post/by/user")
	public Set<PostDTO> getAllPost(@PathVariable(value = "userID") String userId, @PathVariable(value = "page", required = false) int pageNumber) throws ClassNotFoundException {

		return postService
				.getPostByUser(UUID.fromString(userId), pageNumber)
				.stream()
				.map(map -> {
					try {
						return (PostDTO) EntityDtoConversion.ConvertToDTO(map);
					} catch (ClassNotFoundException e) {
						throw new RuntimeException(e);
					}
				})
				.collect(Collectors.toSet());
	}

	@PostMapping(value = "/post/create", consumes = {"application/xml", "application/json"})
	public ResponseEntity<?> createPost(@RequestBody NewPostForm newPostForm, HttpServletRequest request, HttpServletResponse response) {
		return postService.createPost(newPostForm, request, response);
	}

	@PostMapping(value = "/post/edit")
	public ResponseEntity<?> editPost(@RequestBody PostDTO postDTO, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {

		return postService.editPost((Post) EntityDtoConversion.ConvertToEntity(postDTO), request, response);
	}

	@PostMapping("/post/delete")
	public ResponseEntity<?> removePost(@PathVariable("postId") UUID id, HttpServletRequest request, HttpServletResponse response) {

		return postService.removePost(id, request, response);
	}

	@PostMapping("/tag/create")
	public ResponseEntity<?> createTag(@RequestBody NewTagForm newTagForm, HttpServletRequest request, HttpServletResponse response) throws ClassNotFoundException {
		return postService.createTag(newTagForm, request, response);
	}

	@PostMapping("/category/create")
	public ResponseEntity<?> createCategory(@RequestBody NewCategoryForm newCategoryForm, HttpServletRequest request, HttpServletResponse response) {

		return postService.createCategory(newCategoryForm, request, response);
	}
}