package com.team1.sa56.caps.exception;

public class LecturerNotFound extends Exception{
        private static final long serialVersionUID = 1L;
    
    public LecturerNotFound(){};

    public LecturerNotFound(String msg){
        super(msg);
    }    
}
