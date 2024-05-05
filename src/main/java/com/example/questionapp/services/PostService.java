package com.example.questionapp.services;

import com.example.questionapp.entities.Post;
import com.example.questionapp.entities.User;
import com.example.questionapp.repositories.PostRepository;
import com.example.questionapp.requests.PostCreateRequest;
import com.example.questionapp.requests.PostUpdateRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    private PostRepository postRepository;
    private UserService userService;

    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }

    public List<Post> getAllPosts(Optional<Long> userId) {
        if(userId.isPresent()){
            return postRepository.findByUserId(userId.get());
        }
        else{
            return postRepository.findAll();
        }
    }

    public Post getOnePostById(Long postId) {
        return postRepository.findById(postId).orElse(null);
    }

    public Post createOnePost(PostCreateRequest newPostCreateRequst) {

        User user = userService.getOneUserById(newPostCreateRequst.getUserId());
        if(user==null){
            return null;
        }
        else {
            Post postToSave = new Post();
            postToSave.setId(newPostCreateRequst.getId());
            postToSave.setUser(user);
            postToSave.setTitle(newPostCreateRequst.getTitle());
            postToSave.setText(newPostCreateRequst.getText());
            return postRepository.save(postToSave);
        }
    }

    public Post updateOnePostById(Long postId, PostUpdateRequest postUpdateRequest) {
        Optional<Post> post = postRepository.findById(postId);
        if(post.isPresent()){
            Post toUpdate = post.get();
            toUpdate.setText(postUpdateRequest.getText());
            toUpdate.setTitle(postUpdateRequest.getTitle());
            postRepository.save(toUpdate);
            return toUpdate;
        }
        return null;
    }

    public void deletePostById(Long postId) {
        postRepository.deleteById(postId);
    }
}
