package com.team1.sa56.caps.exception;

public class UserNotFound extends Exception {
    private static final long serialVersionUID = 1L;

    public UserNotFound(){};

    public UserNotFound(String msg){
        super(msg);
    }
}