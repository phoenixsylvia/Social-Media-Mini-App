package com.phoenix.facebookminiapp;

import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.entities.User;
import com.phoenix.facebookminiapp.repository.PostRepository;
import com.phoenix.facebookminiapp.service.PostService;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

@SpringBootTest
class PostServiceImplTest {

    @Autowired
    private PostService service;

    @MockBean
    private PostRepository repository;

    @Test
    void createPost() {
        User user = new User("Jon", "Snow", "jon_snow", "1234");
        user.setId(1L);

        Post newPost = new Post("This is a test post", user);
        newPost.setId(2L);
        newPost.setUsers(user);

        when(repository.save(newPost)).thenReturn(newPost);

        assertEquals(2L, service.createPost(newPost).getId());
        assertEquals(1L, service.createPost(newPost).getUsers().getId());
    }

    @Test
    void getAllPosts() {
        User jon = new User("Jon", "Snow", "jon_snow", "1234");
        User tony = new User("Tony", "Stark", "ironMan", "1234");
        when(repository.findAll()).thenReturn(List.of(
                new Post("This is the first post", tony),
                new Post("Jon is the good guy, i think.", jon),
                new Post("Tony is the rich guy, i know this.", tony)
        ));

        assertEquals(3, service.getAllPosts().size());
    }

    @Test
    void getPost() {
        User tony = new User("Tony", "Stark", "ironMan", "1234");
        Post post = new Post("This is the first post", tony);
        post.setId(2L);

        when(repository.findById(2L)).thenReturn(Optional.of(post));

        assertEquals("ironMan", service.getPost(2L).getUsers().getUsername());
        assertEquals(2L, service.getPost(2L).getId());
    }

    @Test
    void updatePost() {
        User user = new User("Jon", "Snow", "jon_snow", "1234");
        user.setId(1L);

        Post updatedPost = new Post("This is post has been updated", user);
        updatedPost.setId(2L);
        updatedPost.setUsers(user);

        service.updatePost(updatedPost);
        verify(repository, times(1)).save(updatedPost);
    }

    @Test
    void deletePost() {
        User tony = new User("Tony", "Stark", "ironMan", "1234");
        Post post = new Post("This is a post to delete", tony);
        post.setId(2L);

        service.deletePost(post.getId());
        verify(repository, times(1)).deleteById(post.getId());
    }
}