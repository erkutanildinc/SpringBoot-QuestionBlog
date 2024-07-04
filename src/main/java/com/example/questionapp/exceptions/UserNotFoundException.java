package com.example.questionapp.exceptions;

import com.example.questionapp.entities.User;

public class UserNotFoundException extends RuntimeException{

    public UserNotFoundException(){
        super();

    }

    public UserNotFoundException(String message){
        super(message);

    }
}
