package com.team1.sa56.caps.controller;

import com.team1.sa56.caps.model.*;
import com.team1.sa56.caps.service.CourseService;
import com.team1.sa56.caps.service.EnrollmentService;
import com.team1.sa56.caps.service.LecturerService;
import com.team1.sa56.caps.service.StudentService;
import com.team1.sa56.caps.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class DashboardController {

    @Autowired
    CourseService courseService;
    
    @Autowired
    LecturerService lectService;

    @Autowired
    StudentService studService;

    @Autowired
    EnrollmentService enrolservice;
    
    @Autowired
    UserService userService;

    @GetMapping("/admin/dashboard")
    public String AdminDashboard(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        List<Student> studentList = studService.findAll();
        List<Lecturer> lecturers = lectService.findAll();
        List<Course> courses = courseService.findAll();
        List<Enrollment> enrollmentList = enrolservice.findAll();
        List<User> users = userService.findAll();
        
        model.addAttribute("studentList",studentList);
        model.addAttribute("lecturers",lecturers);
        model.addAttribute("courses",courses);
        model.addAttribute("enrollmentList",enrollmentList);
        model.addAttribute("users", users);
        model.addAttribute("user", user);
        
        return "admin/admin-dashboard";
    }
}
