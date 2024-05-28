package com.example.questionapp.services;

import com.example.questionapp.entities.Like;
import com.example.questionapp.entities.Post;
import com.example.questionapp.entities.User;
import com.example.questionapp.repositories.PostRepository;
import com.example.questionapp.requests.PostCreateRequest;
import com.example.questionapp.requests.PostUpdateRequest;
import com.example.questionapp.response.LikeResponse;
import com.example.questionapp.response.PostResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class PostService {

    private final PostRepository postRepository;
    private LikeService likeService;
    private final UserService userService;

    public PostService(PostRepository postRepository, UserService userService){
        this.postRepository = postRepository;
        this.userService = userService;
    }
    @Autowired
    @Lazy
    public void setLikeService(LikeService likeService){
        this.likeService = likeService;
    }

    public List<PostResponse> getAllPosts(Optional<Long> userId) {
        List<Post> list;
        if(userId.isPresent()){
            list = postRepository.findByUserId(userId.get());
        }
        else{
            list = postRepository.findAll();
        }
        return list.stream().map(post -> {
            List<LikeResponse> likes = likeService.getAllLikesWithParam(Optional.ofNullable(null),Optional.of(post.getId()));
            return new PostResponse(post,likes);
        }).collect(Collectors.toList());
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
