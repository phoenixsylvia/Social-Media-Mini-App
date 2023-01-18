package com.phoenix.facebookminiapp.controller;

import com.phoenix.facebookminiapp.entities.Comment;
import com.phoenix.facebookminiapp.entities.Like;
import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.entities.User;
import com.phoenix.facebookminiapp.service.LikeService;
import com.phoenix.facebookminiapp.service.PostService;
import com.phoenix.facebookminiapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

    @Controller
    public class PostController {
        private final PostService postService;
        private final UserService userService;
        private final LikeService likeService;

        public PostController(PostService postService, UserService userService, LikeService likeService) {
            this.postService = postService;
            this.userService = userService;
            this.likeService = likeService;
        }

        @GetMapping("/home")
        public String home(Model model, HttpServletRequest request) {
            String username = (String) request.getSession().getAttribute("username");
            if (username == null) return "redirect:/login";

            Post post = new Post();

            List<Post> posts = postService.getAllPosts();
            posts.sort(Comparator.reverseOrder());

            model.addAttribute("post", post);
            model.addAttribute("posts", posts);

            return "home";
        }

        @PostMapping("/home")
        public String createPost(@ModelAttribute("post") Post post, HttpServletRequest request) {
            if (post.getBody().trim().equals("")) {
                return "redirect:/home";
            }
            Long id = (Long) request.getSession().getAttribute("id");
            User currentUser = userService.findById(id);
            post.setUsers(currentUser);

            postService.createPost(post);

            return "redirect:/home";
        }

        @GetMapping("/posts/{id}")
        public String getPost(@PathVariable Long id, Model model, HttpServletRequest request) {
            String username = (String) request.getSession().getAttribute("username");
            if (username == null) return "redirect:/login";

            Comment comment = new Comment();
            Post post = postService.getPost(id);
            List<Comment> comments = post.getComments();
            comments.sort(Comparator.reverseOrder());

            model.addAttribute("post", post);
            model.addAttribute("comment", comment);
            model.addAttribute("comments", comments);

            return "post";
        }

        @GetMapping("/posts/edit/{id}")
        public String editPostForm(@PathVariable Long id, Model model, HttpServletRequest request) {
            String username = (String) request.getSession().getAttribute("username");
            if (username == null) return "redirect:/login";

            Post post = postService.getPost(id);
            model.addAttribute("post", post);

            return "edit_post";
        }

        @PostMapping("/posts/edit/{id}")
        public String editPost(@PathVariable Long id, @ModelAttribute("post") Post post) {
            Post updatePost = postService.getPost(id);
            updatePost.setId(id);
            updatePost.setBody(post.getBody());

            postService.updatePost(updatePost);

            return "redirect:/posts/" + id;
        }

        @GetMapping("/posts/delete/{id}")
        public String deletePost(@PathVariable Long id, HttpServletRequest request) {
            String username = (String) request.getSession().getAttribute("username");
            if (username == null) return "redirect:/login";

            postService.deletePost(id);
            return "redirect:/home";
        }


        @GetMapping("/posts/like/{postId}")
        public String likePost(@PathVariable Long postId, HttpServletRequest request) {
            String username = (String) request.getSession().getAttribute("username");
            if (username == null) return "redirect:/login";

            Post likedPost = postService.getPost(postId);
            Long userId = (Long) request.getSession().getAttribute("id");
            User currentUser = userService.findById(userId);

            Optional<Like> liked = likeService.getPostLikeByUser(likedPost, currentUser);

            if (liked.isPresent()) {
                likeService.removeLike(liked.get().getId());
            } else {
                Like newLike = new Like();
                newLike.setPosts(likedPost);
                newLike.setUsers(currentUser);

                likeService.addLike(newLike);
            }

            return "redirect:/home";
        }

    }

