package com.phoenix.facebookminiapp.service;

import com.phoenix.facebookminiapp.entities.User;


public interface UserService {
    User createUser(User user);

    User findUserByUsernameAndPassword(User user);

    User findById(Long id);
}
