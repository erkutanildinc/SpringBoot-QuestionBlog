package com.example.questionapp.controllers;

import com.example.questionapp.entities.Post;
import com.example.questionapp.requests.PostCreateRequest;
import com.example.questionapp.requests.PostUpdateRequest;
import com.example.questionapp.response.PostResponse;
import com.example.questionapp.services.PostService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/posts")
@CrossOrigin
public class PostController {

    private PostService postService;

    public PostController(PostService postService){
        this.postService = postService;
    }

    @GetMapping
    public List<PostResponse> getAllPosts(@RequestParam Optional<Long> userId){
        return postService.getAllPosts(userId);
    }

    @GetMapping("/{postId}")
    public PostResponse getOnePost(@PathVariable Long postId){
        return postService.getOnePostByIdWithLikes(postId);
    }

    @PostMapping
    public Post createOnePost(@RequestBody PostCreateRequest newPostRequest){
        return postService.createOnePost(newPostRequest);
    }

    @PutMapping("/{postId}")
    public Post updateOnePost(@PathVariable Long postId, @RequestBody PostUpdateRequest postUpdateRequest){
        return postService.updateOnePostById(postId,postUpdateRequest);
    }

    @DeleteMapping("/{postId}")
    public void deletePost(@PathVariable Long postId){
        postService.deletePostById(postId);
    }

}
