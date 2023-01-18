package com.phoenix.facebookminiapp;

import com.phoenix.facebookminiapp.entities.Comment;
import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.entities.User;
import com.phoenix.facebookminiapp.repository.CommentRepository;
import com.phoenix.facebookminiapp.service.CommentService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.mockito.Mockito.*;


    @SpringBootTest
    class CommentServiceImplTest {
        @Autowired
        private CommentService service;
        @MockBean
        private CommentRepository repository;

        @Test
        void createComment() {
            User user = new User("Jon", "Snow", "jon_snow", "1234");
            user.setId(1L);

            Post newPost = new Post("This is a test post", user);
            newPost.setId(2L);
            newPost.setUsers(user);

            Comment newComment = new Comment();
            newComment.setBody("This is a comment");
            newComment.setId(1L);
            newComment.setUsers(user);
            newComment.setPosts(newPost);

            when(repository.save(newComment)).thenReturn(newComment);

            assertEquals(1L, service.createComment(newComment).getId());
            assertEquals(2L, service.createComment(newComment).getPosts().getId());
            assertEquals(1L, service.createComment(newComment).getUsers().getId());
        }

        @Test
        void getComment() {
            User tony = new User("Tony", "Stark", "ironMan", "1234");
            Post post = new Post("This is the first post", tony);
            post.setId(2L);

            Comment comment = new Comment();
            comment.setBody("This is a comment");
            comment.setId(1L);
            comment.setUsers(tony);
            comment.setPosts(post);

            when(repository.findById(2L)).thenReturn(Optional.of(comment));

            assertEquals("ironMan", service.getComment(2L).getUsers().getUsername());
            assertEquals(2L, service.getComment(2L).getPosts().getId());
            assertEquals(1L, service.getComment(2L).getId());
        }

        @Test
        void updateComment() {

            Comment comment = new Comment();
            comment.setBody("This is a comment");
            comment.setId(1L);

            service.updateComment(comment);
            verify(repository, times(1)).save(comment);
        }

        @Test
        void deleteComment() {
            Comment comment = new Comment();
            comment.setBody("This is a comment");
            comment.setId(1L);

            service.deleteComment(comment.getId());
            verify(repository, times(1)).deleteById(comment.getId());
        }
    }

