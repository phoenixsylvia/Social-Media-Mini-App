package com.phoenix.facebookminiapp.controller;

import com.phoenix.facebookminiapp.entities.Comment;
import com.phoenix.facebookminiapp.entities.Like;
import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.entities.User;
import com.phoenix.facebookminiapp.service.CommentService;
import com.phoenix.facebookminiapp.service.LikeService;
import com.phoenix.facebookminiapp.service.PostService;
import com.phoenix.facebookminiapp.service.UserService;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import jakarta.servlet.http.HttpServletRequest;

import java.util.Optional;

@Controller
public class CommentController {

    private final CommentService commentService;
    private final PostService postService;
    private final UserService userService;
    private final LikeService likeService;

    public CommentController(CommentService commentService, PostService postService, UserService userService, LikeService likeService) {
        this.commentService = commentService;
        this.postService = postService;
        this.userService = userService;
        this.likeService = likeService;
    }

    @PostMapping("/comments/{id}")
    public String createComment(@PathVariable Long id, @ModelAttribute("comment") Comment comment, HttpServletRequest request){

        if(comment.getBody().trim().equals("")){
            return "redirect:/posts/" + id;
        }

        Long userId = (Long) request.getSession().getAttribute("id");
        User currentUser = userService.findById(userId);
        Post post = postService.getPost(id);

        Comment newComment = new Comment();

        newComment.setBody(comment.getBody());
        newComment.setUsers(currentUser);
        newComment.setPosts(post);

        commentService.createComment(newComment);

        return "redirect:/posts/" + id;
    }

    @GetMapping("/comments/edit/{id}")
    public String editCommentForm(@PathVariable Long id, Model model, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(username == null) return "redirect:/login";

        Comment comment = commentService.getComment(id);
        model.addAttribute("comment", comment);

        return "edit_comment";
    }

    @PostMapping("/comments/edit/{id}")
    public String editComment(@PathVariable Long id, @ModelAttribute("comment") Comment comment){
        Comment updateComment = commentService.getComment(id);
        updateComment.setId(id);
        updateComment.setBody(comment.getBody());

        commentService.updateComment(updateComment);

        return "redirect:/posts/" + updateComment.getPosts().getId();
    }


    @GetMapping("/comments/delete/{id}")
    public String deleteComment(@PathVariable Long id, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(username == null) return "redirect:/login";

        Comment comment = commentService.getComment(id);
        Long postId = comment.getPosts().getId();

        commentService.deleteComment(id);
        return "redirect:/posts/" + postId;
    }

    @GetMapping("/comments/like/{commentId}")
    public String likeComment(@PathVariable Long commentId, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(username == null) return "redirect:/login";

        Comment likedComment = commentService.getComment(commentId);
        Long userId = (Long) request.getSession().getAttribute("id");
        User currentUser = userService.findById(userId);

        Optional<Like> liked = likeService.getCommentLikeByUser(likedComment, currentUser);

        if(liked.isPresent()){
            likeService.removeLike(liked.get().getId());
        } else {
            Like newLike = new Like();
            newLike.setComments(likedComment);
            newLike.setUsers(currentUser);

            likeService.addLike(newLike);
        }

        Long postId = likedComment.getPosts().getId();

        return "redirect:/posts/" + postId;
    }
}

