package com.phoenix.facebookminiapp.controller;

import com.phoenix.facebookminiapp.entities.Like;
import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.entities.User;
import com.phoenix.facebookminiapp.service.LikeService;
import com.phoenix.facebookminiapp.service.PostService;
import com.phoenix.facebookminiapp.service.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import java.util.Objects;
import java.util.Optional;

@Controller
public class UserController {
    private final UserService userService;
    private final PostService postService;
    private final LikeService likeService;

    public UserController(UserService userService, PostService postService, LikeService likeService) {
        this.userService = userService;
        this.postService = postService;
        this.likeService = likeService;
    }

    @GetMapping("/signup")
    public String registerUserForm(Model model){
        User user = new User();
        model.addAttribute("user", user);
        return "signup";
    }

    @PostMapping("/signup")
    public String registerUser(@ModelAttribute("user") User user, Model model){
        User newUser = userService.createUser(user);
        model.addAttribute("success", newUser);

        return "redirect:/login?action=success";
    }

    @GetMapping("/login")
    public String loginUserForm(Model model, HttpServletRequest request){
        if(Objects.equals(request.getParameter("action"), "success")){
            model.addAttribute("success", true);
        }

        User user = new User();
        model.addAttribute("user", user);
        return "login";
    }

    @PostMapping("/login")
    public String loginUser(@ModelAttribute("user") User user, HttpServletRequest request, HttpServletResponse response){
        User loggedInUser = userService.findUserByUsernameAndPassword(user);

        if(loggedInUser == null){
            return "redirect:/login";
        }

        request.getSession().invalidate();
        HttpSession newSession = request.getSession(true);
        newSession.setMaxInactiveInterval(300);
        newSession.setAttribute("id", loggedInUser.getId());
        newSession.setAttribute("username", loggedInUser.getUsername());
        String encodeUrl = response.encodeURL(request.getContextPath());

        return "redirect:"+ encodeUrl+"/home";
    }


    @GetMapping("users/{id}")
    public String userPosts(@PathVariable Long id, Model model, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(username == null) return "redirect:/login";

        User user = userService.findById(id);

        model.addAttribute("user", user);

        return "user";
    }

    @GetMapping("/users/like/{postId}")
    public String likePost(@PathVariable Long postId, HttpServletRequest request){
        String username = (String) request.getSession().getAttribute("username");
        if(username == null) return "redirect:/login";

        Long userId = (Long) request.getSession().getAttribute("id");
        User currentUser = userService.findById(userId);
        Post likedPost = postService.getPost(postId);

        Optional<Like> liked = likeService.getPostLikeByUser(likedPost, currentUser);

        if(liked.isPresent()){
            likeService.removeLike(liked.get().getId());
        } else {
            Like newLike = new Like();
            newLike.setUsers(currentUser);
            newLike.setPosts(likedPost);

            likeService.addLike(newLike);
        }

        return "redirect:/users/" + userId;
    }

    @GetMapping("/logout")
    public String logout(HttpServletRequest request){
        request.getSession().invalidate();

        return "redirect:/";
    }
}
