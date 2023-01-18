package com.phoenix.facebookminiapp.service;

import com.phoenix.facebookminiapp.entities.Comment;
import com.phoenix.facebookminiapp.entities.Like;
import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.entities.User;

import java.util.Optional;

public interface LikeService {
    void addLike(Like like);
    void removeLike(Long id);

    Optional<Like> getPostLikeByUser(Post post, User user);
    Optional<Like> getCommentLikeByUser(Comment comment, User user);
}
