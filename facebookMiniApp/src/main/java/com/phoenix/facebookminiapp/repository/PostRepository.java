package com.phoenix.facebookminiapp.repository;

import com.phoenix.facebookminiapp.entities.Post;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
}
