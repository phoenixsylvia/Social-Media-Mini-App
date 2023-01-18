package com.phoenix.facebookminiapp;

import com.phoenix.facebookminiapp.entities.User;
import com.phoenix.facebookminiapp.repository.UserRepository;
import com.phoenix.facebookminiapp.service.UserService;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@SpringBootTest
class UserServiceImplTest {

    @Autowired
    private UserService service;

    @MockBean
    private UserRepository repository;

    @Test
    void testCreateUser() {
        User newUser = new User("Jon", "Snow", "jon@email.com", "1234");

        when(repository.save(newUser))
                .thenReturn(newUser);

        assertEquals(newUser, service.createUser(newUser));
    }

    @Test
    void testFindUserByUsernameAndPassword() {
        User user = new User("Tony", "Stark", "iron_man", "4321");
        user.setId(3L);

        when(repository.findByUsernameAndPassword("iron_man", "4321"))
                .thenReturn(Optional.of(user));

        assertEquals(3L, service.findUserByUsernameAndPassword(user).getId());
    }

    @Test
    void testFindById() {
        User user = new User("Jon", "Snow", "jon_snow", "1234");
        user.setId(1L);

        when(repository.findById(1L))
                .thenReturn(Optional.of(user));

        assertEquals("jon_snow", service.findById(1L).getUsername());
    }
}