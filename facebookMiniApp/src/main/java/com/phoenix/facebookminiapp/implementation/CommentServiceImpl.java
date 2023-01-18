package com.phoenix.facebookminiapp.implementation;

import com.phoenix.facebookminiapp.entities.Comment;
import com.phoenix.facebookminiapp.repository.CommentRepository;
import com.phoenix.facebookminiapp.service.CommentService;

import org.springframework.stereotype.Service;

@Service
public class CommentServiceImpl implements CommentService {

    private final CommentRepository commentRepository;

    public CommentServiceImpl(CommentRepository commentRepository) {
        this.commentRepository = commentRepository;
    }

    @Override
    public Comment createComment(Comment comment) {
        return commentRepository.save(comment);
    }

    @Override
    public Comment getComment(Long id) {
        return commentRepository.findById(id).get();
    }

    @Override
    public void updateComment(Comment updateComment) {
        commentRepository.save(updateComment);
    }

    @Override
    public void deleteComment(Long id) {
        commentRepository.deleteById(id);
    }
}
