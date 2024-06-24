package com.example.questionapp.services;

import com.example.questionapp.entities.Comment;
import com.example.questionapp.entities.Like;
import com.example.questionapp.entities.User;
import com.example.questionapp.repositories.CommentRepository;
import com.example.questionapp.repositories.LikeRepository;
import com.example.questionapp.repositories.PostRepository;
import com.example.questionapp.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    private final CommentRepository commentRepository;

    private final PostRepository postRepository;

    public UserService(UserRepository userRepository, LikeRepository likeRepository, CommentRepository commentRepository, PostRepository postRepository){
        this.userRepository = userRepository;
        this.likeRepository = likeRepository;
        this.commentRepository = commentRepository;
        this.postRepository = postRepository;
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public User addUser(User newUser) {
        return userRepository.save(newUser);
    }

    public User getOneUserById(Long userId) {
        return userRepository.findById(userId).orElse(null);
    }

    public User updateUser(Long userId, User newUser) {
        Optional<User> user = userRepository.findById(userId);
        if(user.isPresent()){
            User foundUser = user.get();
            foundUser.setAvatar(newUser.getAvatar());
            foundUser.setUsername(newUser.getUsername());
            foundUser.setPassword(newUser.getPassword());
            userRepository.save(foundUser);
            return foundUser;
        }
        else{
            return null;
        }
    }

    public void deleteById(Long userId) {
        userRepository.deleteById(userId);
    }

    public User getOneUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<Object> getUserActivity(Long userId) {
        List<Long> postIds = postRepository.findTopByUserId(userId);
        if(postIds.isEmpty()){
            return null;
        }
        else{
            List<Object> userComments =commentRepository.findUserCommentsByPostId(postIds);
            List<Object> userLikes = likeRepository.findUserLikesByPostId(postIds);
            List<Object> result = new ArrayList<>();
            result.addAll(userComments);
            result.addAll(userLikes);
            return result;
        }
    }
}
