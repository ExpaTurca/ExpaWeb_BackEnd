/*
 * Copyright (c) 2022. Lorem ipsum dolor sit amet, consectetur adipiscing elit.
 * Morbi non lorem porttitor neque feugiat blandit. Ut vitae ipsum eget quam lacinia accumsan.
 * Etiam sed turpis ac ipsum condimentum fringilla. Maecenas magna.
 * Proin dapibus sapien vel ante. Aliquam erat volutpat. Pellentesque sagittis ligula eget metus.
 * Vestibulum commodo. Ut rhoncus gravida arcu.
 */

package com.expastudios.blogweb.services;

import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.model.PostDTO;

import java.util.List;
import java.util.Optional;

public interface PostService {
	public boolean createPost(PostDTO postDTO, String email);

	public boolean editPost(PostDTO postDTO) ;

	public boolean removePost(long Id);

	public Optional<PostDTO> getPost(long postId);

	public List<Post> getAllPostByUserId(String userIdl, int page);
}
