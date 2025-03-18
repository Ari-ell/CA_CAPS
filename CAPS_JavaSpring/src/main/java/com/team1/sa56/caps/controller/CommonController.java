package com.team1.sa56.caps.controller;

import com.team1.sa56.caps.model.User;
import com.team1.sa56.caps.service.UserService;


import com.team1.sa56.caps.validator.UserValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.Valid;

@Controller
public class CommonController {
    
    @Autowired
    UserService userService;

    @Autowired
    UserValidator userValidator;
    
    @InitBinder
    private void initUserBinder(WebDataBinder binder) {
        binder.addValidators(userValidator);
    }
    
    @GetMapping({"/login","/"})
    public String login(Model model) {
        User user = new User();
        model.addAttribute(user);
        return "login";
    }

    @PostMapping("/login")
    public String login(Model model, HttpSession httpSession, 
                @Valid @ModelAttribute User user, BindingResult result) {
        if(result.hasErrors()){
            return "/login";
        }

        User foundUser =  userService.validateAccount(user);
        if(foundUser == null) {
            model.addAttribute("errorMessage", "Wrong email or password");
            return "/login";
        }
    
        foundUser.setPassword("");
        httpSession.setAttribute("user", foundUser);
        
        // redirect to respective page
        if (foundUser.getRole().equals("Admin")) {
            return "redirect:/admin/dashboard";
        }
        if (foundUser.getRole().equals("Lecturer")) {
            return "redirect:/lecturer/home";
        }
        if (foundUser.getRole().equals("Student")) {
            return "redirect:/student/home";
        }
        return "/login";
    }

    @GetMapping(value = "/logout")
    public String logout(HttpSession session){
        session.invalidate();
        return "/logout";
    }
}
