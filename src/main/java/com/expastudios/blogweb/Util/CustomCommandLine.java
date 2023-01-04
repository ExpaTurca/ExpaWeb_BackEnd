/***************************************************************
 * Copyright (c) 2023
 **************************************************************/
package com.expastudios.blogweb.Util;

import com.expastudios.blogweb.entity.DTOs.AccountDTO;
import com.expastudios.blogweb.entity.DTOs.PostDTO;
import com.expastudios.blogweb.entity.Forms.NewCommentForm;
import com.expastudios.blogweb.entity.Forms.NewPostForm;
import com.expastudios.blogweb.entity.Forms.NewUserForm;
import com.expastudios.blogweb.services.AccountService;
import com.expastudios.blogweb.services.CommentService;
import com.expastudios.blogweb.services.PostService;
import com.github.javafaker.Faker;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;
import java.util.UUID;

@Component
public class CustomCommandLine implements CommandLineRunner {

    private final AccountService accountService;
    private final PostService postService;
    private final CommentService commentService;

    public CustomCommandLine(AccountService accountService, PostService postService, CommentService commentService) {
        this.accountService = accountService;
        this.postService = postService;
        this.commentService = commentService;
    }

    @Override
    public void run(String... args) throws Exception {

        //Kullanıcı Oluştur.
        for (int i = 0; i < 10; i++) {
            NewUserForm userForm = new NewUserForm();
            Faker fakerAccount = new Faker();
            userForm.setFirstName(fakerAccount.name().firstName());
            userForm.setLastName(fakerAccount.name().lastName());
            userForm.setUsername(fakerAccount.name().username());
            userForm.setEmail(fakerAccount.internet().emailAddress());
            userForm.setPassword(fakerAccount.internet().password(
                    8,
                    16,
                    true,
                    true,
                    true));

            System.out.println(userForm.getUsername() + ":" + userForm.getPassword());

            accountService.create(
                    userForm
            );

        }

        List<AccountDTO> acc = accountService.getAll();

        //Paylaşım Oluştur.
        for (int i = 0; i < 10; i++) {
            Faker fakerPost = new Faker();
            NewPostForm postForm = new NewPostForm();
            postForm.setTitle(fakerPost.lorem().characters(8, 32));
            postForm.setMetaTitle(String.join(",", fakerPost.lorem().words(3)));
            postForm.setContent(fakerPost.lorem().paragraph(3));

            Random rnd = new Random();
            int listItem = rnd.nextInt(0, acc.size());
            String selectedEmail = acc.get(listItem).getEmail();
            System.out.println(selectedEmail + ":" + postForm.getTitle());
            postService.create(postForm, selectedEmail);
        }

        List<PostDTO> ptt = postService.getAll();

        //Yorum Yap.
        for (int i = 0; i < 10; i++) {
            Faker fakerPost = new Faker();
            Random rnd = new Random();
            int listItem = rnd.nextInt(0, acc.size());
            int listPost = rnd.nextInt(0, ptt.size());
            String selectedEmail = acc.get(listItem).getEmail();
            UUID selectedPostId = ptt.get(listPost).getId();
            NewCommentForm commentForm = new NewCommentForm();
            commentForm.setComment(fakerPost.lorem().paragraph(3));
            commentForm.setPostId(selectedPostId);

            System.out.println(selectedEmail + ":" + selectedPostId + ":" + commentForm.getComment());
            commentService.create(commentForm, selectedEmail);
        }
    }
}