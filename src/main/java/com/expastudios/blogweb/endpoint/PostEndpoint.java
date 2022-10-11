package com.expastudios.blogweb.endpoint;

import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.model.PostDTO;
import com.expastudios.blogweb.services.PostServiceImp;
import io.jsonwebtoken.Jwts;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Optional;

import static com.expastudios.blogweb.Util.SecurityContextVariables.Prefix;
import static com.expastudios.blogweb.Util.SecurityContextVariables.Secret;



@Slf4j
@RestController
@RequestMapping("/post")
public class PostEndpoint {

	@Autowired
	private PostServiceImp postServiceImp;

	@GetMapping("/{Id}")
	public Optional<PostDTO> getPost(@PathVariable(value = "Id") long postId) {
		return postServiceImp.getPost(postId);
	}

	@GetMapping("/list/{userId}&pg={pageNumber}")
	public List<Post> getAllPost(
			@PathVariable(value = "userId") String userId, @PathVariable(value = "pageNumber") int pageNumber
															) {
		return postServiceImp.getAllPostByUserId(userId, pageNumber);
	}

	@PostMapping("/new")
	public boolean createPost(@RequestBody PostDTO postDTO, HttpServletRequest request, HttpServletResponse response) {
		String token = request.getHeader("JSESSIONID");
		String user = null;
		if (token != null) {
			try {
				user = Jwts.parser().setSigningKey(Secret).parseClaimsJws(token.replace(Prefix + " ", "")).getBody().getSubject();
			} catch (Exception e) {
				log.error(e.getLocalizedMessage());
			}
		}
		return postServiceImp.createPost(postDTO, user);
	}

	@PostMapping("/edit")
	public boolean editPost(@RequestBody PostDTO postDTO) {
		return postServiceImp.editPost(postDTO);
	}

	@PostMapping("/remove/{id}")
	public boolean disposePost(@PathVariable(value = "id") long id) {
		return postServiceImp.removePost(id);
	}
}
