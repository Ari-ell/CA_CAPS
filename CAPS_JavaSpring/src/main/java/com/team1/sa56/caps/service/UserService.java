package com.team1.sa56.caps.service;

import java.util.List;

import com.team1.sa56.caps.model.User;

public interface UserService {
    
    User validateAccount(User user);

    List<User> findAll();
}
