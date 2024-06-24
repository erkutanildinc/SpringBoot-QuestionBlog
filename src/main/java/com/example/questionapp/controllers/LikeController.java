package com.example.questionapp.controllers;

import com.example.questionapp.entities.Like;
import com.example.questionapp.requests.LikeCreateRequest;
import com.example.questionapp.response.LikeResponse;
import com.example.questionapp.services.LikeService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/likes")
@CrossOrigin
public class LikeController {

    private final LikeService likeService;

    public LikeController(LikeService likeService) {
        this.likeService = likeService;
    }

    @GetMapping
    public List<LikeResponse> getAllLikes(@RequestParam Optional<Long> userId, Optional<Long> postId){
        return likeService.getAllLikesWithParam(userId, postId);
    }

    @GetMapping("/{likeId}")
    public Like getOneLike(@PathVariable Long likeId) {
        return likeService.getOneLikeById(likeId);
    }

    @PostMapping
    public Like createOneLike(@RequestBody LikeCreateRequest request) {
        return likeService.createLike(request);
    }

    @DeleteMapping("/{likeId}")
    public void deleteOneLike(@PathVariable Long likeId) {
        likeService.deleteLike(likeId);
    }

}
