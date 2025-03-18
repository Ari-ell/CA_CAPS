package com.team1.sa56.caps.exception;

public class Unauthorized extends Exception {
    public Unauthorized(){};

    public Unauthorized(String msg){
        super(msg);
    }
}