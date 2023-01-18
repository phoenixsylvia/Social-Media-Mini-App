package com.phoenix.facebookminiapp.implementation;

import com.phoenix.facebookminiapp.entities.Post;
import com.phoenix.facebookminiapp.repository.PostRepository;
import com.phoenix.facebookminiapp.service.PostService;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostServiceImpl implements PostService {

    private final PostRepository postRepository;

    public PostServiceImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }

    @Override
    public Post createPost(Post post) {
        return postRepository.save(post);
    }

    @Override
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    @Override
    public Post getPost(Long id) {
        return postRepository.findById(id).get();
    }

    @Override
    public void updatePost(Post updatePost) {
        postRepository.save(updatePost);
    }

    @Override
    public void deletePost(Long id) {
        postRepository.deleteById(id);
    }
}
