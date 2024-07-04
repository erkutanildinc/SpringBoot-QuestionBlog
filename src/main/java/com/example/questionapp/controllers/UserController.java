package com.example.questionapp.controllers;

import com.example.questionapp.entities.User;
import com.example.questionapp.exceptions.UserNotFoundException;
import com.example.questionapp.repositories.UserRepository;
import com.example.questionapp.response.UserResponse;
import com.example.questionapp.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("/users")
@CrossOrigin
public class UserController {

    private UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAllUsers(){
        return userService.getAllUsers();
    }

    @PostMapping
    public User createUser(@RequestBody User newUser){
        return userService.addUser(newUser);
    }

    @GetMapping("/{userId}")
    public User getOneUser(@PathVariable Long userId){
        User user = userService.getOneUserById(userId);
        if(user==null){
            throw new UserNotFoundException();
        }
        return user;
    }

    @PutMapping("/{userId}")
    public User updateOneUser(@PathVariable Long userId,@RequestBody User newUser){
        return userService.updateUser(userId,newUser);
    }

    @DeleteMapping("/{userId}")
    public void deleteOneUser(@PathVariable Long userId){
        userService.deleteById(userId);
    }

    @GetMapping("/activity/{userId}")
    public List<Object> getUserActivity(@PathVariable Long userId) {return userService.getUserActivity(userId);}

    @ExceptionHandler(UserNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    private void handleUserNotFound(){

    }

}
