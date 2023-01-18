package com.phoenix.facebookminiapp.implementation;

import com.phoenix.facebookminiapp.entities.Comment;
import com.phoenix.facebookminiapp.entities.Like;
import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.entities.User;
import com.phoenix.facebookminiapp.repository.LikeRepository;
import com.phoenix.facebookminiapp.service.LikeService;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class LikeServiceImpl implements LikeService {

    private final LikeRepository likeRepository;

    public LikeServiceImpl(LikeRepository likeRepository) {
        this.likeRepository = likeRepository;
    }

    @Override
    public void addLike(Like like) {
        likeRepository.save(like);
    }

    @Override
    public void removeLike(Long id) {
        likeRepository.deleteById(id);
    }

    @Override
    public Optional<Like> getPostLikeByUser(Post post, User user) {
        return likeRepository.findByPostsAndUsers(post, user);
    }

    @Override
    public Optional<Like> getCommentLikeByUser(Comment comment, User user) {
        return likeRepository.findByCommentsAndUsers(comment, user);
    }
}
