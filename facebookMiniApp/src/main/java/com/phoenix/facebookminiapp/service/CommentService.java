package com.phoenix.facebookminiapp.service;

import com.phoenix.facebookminiapp.entities.Comment;


public interface CommentService {

    Comment createComment(Comment comment);

    Comment getComment(Long id);

    void updateComment(Comment updateComment);

    void deleteComment(Long id);

}
