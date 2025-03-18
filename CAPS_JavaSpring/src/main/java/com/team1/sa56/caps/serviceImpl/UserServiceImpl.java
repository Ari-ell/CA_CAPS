package com.team1.sa56.caps.serviceImpl;

import com.team1.sa56.caps.model.User;
import com.team1.sa56.caps.repository.UserRepository;
import com.team1.sa56.caps.service.UserService;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
    
    @Resource
    private UserRepository userRepo;

    @Autowired
    PasswordEncoder passwordEncoder;
    
    @Override
    public User validateAccount(User user) {

       User foundUser =  userRepo.findUserByEmail(user.getEmail());

        if(foundUser != null && passwordEncoder.matches(user.getPassword(), foundUser.getPassword())) {
            return foundUser;   
        }
        return null;
    }

    @Override
    public List<User> findAll() {
        return userRepo.findAll();
    }
}