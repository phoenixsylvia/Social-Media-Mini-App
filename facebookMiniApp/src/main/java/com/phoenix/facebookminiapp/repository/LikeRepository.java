package com.phoenix.facebookminiapp.repository;

import com.phoenix.facebookminiapp.entities.Comment;
import com.phoenix.facebookminiapp.entities.Like;
import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.entities.User;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    Optional<Like> findByPostsAndUsers(Post posts, User users);
    Optional<Like> findByCommentsAndUsers(Comment comments, User users);
}
