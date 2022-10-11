package com.expastudios.blogweb.services;

import com.expastudios.blogweb.Util.Zone;
import com.expastudios.blogweb.entity.Post;
import com.expastudios.blogweb.entity.User;
import com.expastudios.blogweb.model.PostDTO;
import com.expastudios.blogweb.repository.PostRepository;
import com.expastudios.blogweb.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PostServiceImp implements PostService {
    @PersistenceContext
    private EntityManager manager;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PostRepository postRepository;

    public boolean createPost(PostDTO postDTO, String email) {
        try {
            Post postEntity = new Post();
            User user = new User();
            user = userRepository.findByEmail(email).get();

            Thread postThread = new Thread(() -> {
                postEntity.setTitle(postDTO.getTitle());
                postEntity.setContent(postDTO.getContent());
                postEntity.setCreatedAt(Zone.getCurrentTime());
                postRepository.save(postEntity);
            });

            postThread.start();
            postThread.join();

            if (postThread.getState() == Thread.State.TERMINATED) {
                user.getPosts().add(postEntity);
                userRepository.save(user);
            }
            return true;
        } catch (Exception exc) {
            log.error(exc.getLocalizedMessage());
            return false;
        }
    }

    @Transactional
    public boolean editPost(PostDTO postDTO) {
        Post post = new Post();
        post.setTitle(postDTO.getTitle());
        post.setContent(postDTO.getContent());
        try {
            manager.persist(post);
            return true;
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
    }

    @Transactional
    public boolean removePost(long Id) {
        Post post = manager.find(Post.class, Id);
        try {
            manager.remove(post);
            return true;
        } catch (Exception exc) {
            exc.printStackTrace();
            return false;
        }
    }

    public Optional<PostDTO> getPost(long postId) {
        Post post = Objects.requireNonNull(postRepository.findById(postId).stream().findFirst().orElse(null));
        PostDTO postDTO = new PostDTO();
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        return Optional.of(postDTO);
    }

    public List<Post> getAllPostByUserId(String userId, int pageNumber) {
        Pageable page = PageRequest.of(pageNumber, 10, Sort.by("createdAt"));
        Optional<User> user = userRepository.findByEmail(userId);
        Page<Post> post = new PageImpl<>(user.get().getPosts().stream().toList().subList(0, 10), page, user.get().getPosts().stream().toList().size());
        return post.get().toList();
    }
}
