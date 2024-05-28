package com.example.questionapp.services;

import com.example.questionapp.entities.Comment;
import com.example.questionapp.entities.Like;
import com.example.questionapp.entities.Post;
import com.example.questionapp.entities.User;
import com.example.questionapp.repositories.LikeRepository;
import com.example.questionapp.requests.LikeCreateRequest;
import com.example.questionapp.response.LikeResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class LikeService {
    private final LikeRepository likeRepository;
    private final UserService userService;
    private final PostService postService;

    public LikeService(LikeRepository likeRepository, UserService userService, PostService postService) {
        this.likeRepository = likeRepository;
        this.userService = userService;
        this.postService = postService;
    }

    public List<LikeResponse> getAllLikesWithParam(@RequestParam Optional <Long> userId, @RequestParam Optional <Long> postId){
        List<Like> list;
        if(userId.isPresent() && postId.isPresent()){
            list = likeRepository.findByUserIdAndPostId(userId.get(),postId.get());
        }
        else if(userId.isPresent()){
            list = likeRepository.findByUserId(userId.get());
        }
        else if(postId.isPresent()){
            list = likeRepository.findByPostId(postId.get());
        }
        else{
            list = likeRepository.findAll();
        }
        return list.stream().map(like -> new LikeResponse(like)).collect(Collectors.toList());
    }

    public Like getOneLikeById(@PathVariable Long likeId){
        return likeRepository.findById(likeId).orElse(null);
    }

    public Like createLike(@RequestBody LikeCreateRequest likeCreateRequest) {
        User user = userService.getOneUserById(likeCreateRequest.getUserId());
        Post post = postService.getOnePostById(likeCreateRequest.getPostId());
        if (user != null && post != null) {
            Like liketoSave = new Like();
            liketoSave.setPost(post);
            liketoSave.setUser(user);
            return likeRepository.save(liketoSave);
        }
        else
            return null;
    }

    public void deleteLike(@PathVariable Long likeId) {
        likeRepository.deleteById(likeId);
    }
}
